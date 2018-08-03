package com.example.emanuelecalvisi.kotlinapp.Fragments

/*

Created by Emanuele Calvisi on 03/08/2018.

*/

class Person(_firstName: String, _lastName: String) {
  // Member Variables (Properties) of the class
  var firstName: String
  var lastName: String

  // Initializer Block
  init {
    this.firstName = _firstName
    this.lastName = _lastName
    println("Initialized a new Person object with firstName = $firstName and lastName = $lastName")
  }

}