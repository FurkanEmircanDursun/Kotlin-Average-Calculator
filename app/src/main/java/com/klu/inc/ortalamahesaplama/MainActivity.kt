package com.klu.inc.ortalamahesaplama

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.yeni_ders_layout.view.*

class MainActivity : AppCompatActivity() {
    private  var TumDerslerinBilgileri:ArrayList<Dersler> = ArrayList(5)
    private val dersler= arrayOf("Yazılım mimarisi ve Tasarımı","Yapay zekâ ve uzman sistemler","Görsel programlama","Bilgisayar grafikleri","Python programlama","Mesleki ingilizce","Bilgisayar ağları ve iletişim"
    ,"Yönetim bilişim sistemleri")
    var toast: Toast? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var adapter=ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,dersler)
        etDersAd.setAdapter(adapter)




        if(scrollViewLinearLayout.childCount==0){
            btnHesapla.visibility= View.INVISIBLE
        }


        btnDersEkle.setOnClickListener {

            if(!etDersAd.text.isNullOrEmpty()){
                btnHesapla.visibility=View.VISIBLE


                var inflater=LayoutInflater.from(this)
                // var inflater2= layoutInflater  2. tanım taktiği
                //  var inflater3=getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater 3. tanım taktiği
                var yeniDersView = inflater.inflate(R.layout.yeni_ders_layout,null)

                yeniDersView.etYeniDersAd.setAdapter(adapter)
                // etYeni ders adındanda kısaltmaları kullanma

                //statik alandan kullanıcının girdiği değerleri alma

                var   dersAdi=etDersAd.text.toString()
                var   dersKredisi=spnDersKredi.selectedItem.toString()
                var   dersHarfi =spnDersNot.selectedItem.toString()

                yeniDersView.etYeniDersAd.setText(dersAdi)
                yeniDersView.spnYeniDersKredi.setSelection(IndexSayanProgram(spnDersKredi,dersKredisi))
                yeniDersView.spnYeniDersNot.setSelection(IndexSayanProgram(spnDersNot,dersHarfi))



                scrollViewLinearLayout.addView(yeniDersView)
                sıfırla()
                yeniDersView.btnDersSil.setOnClickListener{


                    scrollViewLinearLayout.removeView(yeniDersView)
                    if(scrollViewLinearLayout.childCount==0){
                        btnHesapla.visibility= View.INVISIBLE
                    }
                    else{
                        btnHesapla.visibility=View.VISIBLE
                    }


                }

            }
            else{
                toast?.cancel()
               toast= Toast.makeText(applicationContext,"Ders adı boş bırakılamaz", Toast.LENGTH_SHORT)
                    toast?.show()

            }




        }
    }

    fun ortalamaHesaplama(view: android.view.View) {
        var toplamNot=0.0
        var toplamKredi=0.0



        for (i in 0..   scrollViewLinearLayout.childCount -1 ){

            var tekSatir=scrollViewLinearLayout.getChildAt(i)
            var geciciDers=Dersler(tekSatir.etYeniDersAd.text.toString()
                ,tekSatir.spnYeniDersNot.selectedItem.toString(),
                ((tekSatir.spnYeniDersKredi.selectedItemPosition)+1).toString())

            TumDerslerinBilgileri.add(geciciDers)

            for (oankiDers in TumDerslerinBilgileri){
                toplamNot+=harfiNotaCevir(oankiDers.dersNotu).toDouble()*(oankiDers.dersKredisi.toDouble())
                toplamKredi+=oankiDers.dersKredisi.toDouble()



                Toast.makeText(this, "Ortalamanız :"+(toplamNot/toplamKredi), Toast.LENGTH_LONG).show()
                TumDerslerinBilgileri.clear()

            }



        }

    }
    fun harfiNotaCevir(harf:String): Double {
        var not=0.0
      when(harf){
        "AA"->   not=4.0
        "BA"->   not=3.5
        "BB"->   not=3.0
        "CB"->   not=2.5
        "CC"->   not=2.0
        "DC"->   not=1.5
        "DD"->   not=1.0
        "FF"->   not=0.0


      }


        return not
    }

    fun IndexSayanProgram(spinner: Spinner,arananDeger:String): Int {
        var index=0
        for (i in 0..spinner.count  )
            if(spinner.getItemAtPosition(i).toString()==arananDeger.toString()){
                index=i
                break
            }
    return index

    }
    fun sıfırla(){
        etDersAd.setText("")
        spnDersKredi.setSelection(0)
        spnDersNot.setSelection(0)
    }




}