package com.example.doggeforlife

data class PetsData(
    var id:Int=0,
    var name:String="",
    var url : String="",
    var type : String="",
    var age : Int = 0,
    var vaccinated : Int=0,

)
data class  AllPets(
    var pets : List<PetsData>?=null
)
