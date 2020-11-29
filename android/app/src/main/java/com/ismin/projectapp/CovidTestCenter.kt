package com.ismin.projectapp

import java.io.Serializable

data class CovidTestCenter(
    val ID:            String,
    val rs:            String, //raison sociale
    val adresse:       String,
    val do_prel:       String,
    val do_antigenic:  String,
    val longitude:     String,
    val latitude:      String,
    val mod_prel:      String,
    val public:        String,
    val horaire:       String,
    val check_rdv:     String,
    val tel_rdv:       String,
    val web_rdv:       String,
    val date_modif:    String
) : Serializable
