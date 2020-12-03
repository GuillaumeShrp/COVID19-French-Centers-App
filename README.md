## COVID TEST CENTER

Android Application + Web Server Development

by Guillaume Sherpa and Vivien Debuchy

Students of Mines St Etienne, ISMIN 2020 - M2 Computer Science.

[![Mines St Etienne](./logo.png)](https://www.mines-stetienne.fr/) 

---
### Project

This application allows to list and display all covid test centers in France.

Database of centers from [www.data.gouv.fr](https://www.data.gouv.fr/fr/datasets/sites-de-prelevements-pour-les-tests-covid/), downloaded on a [clevercloud server](covidtesingcenter-app.cleverapps.io).

### Features
- Access all French COVID test centers ✔️
- Bookmark centers ✔️
- Check detailed center information ✔️
- Refresh data base from server ✔️

### Get started !
- Start Android Studio after downloading the project
- Select `Open an existing Android Studio project` and pick this directory

### Technical information:
-    The App is connected to a remote REST API server (Thanks to clervercould.com for free access to their infrastructure). Data are stored into the remote server in JSON format
-    The App contains two fragments (data list and database information)
-    The App contains two activities (main activity and detail activity)
-    In the data list the “car” icon means that the COVID-19 test is done via a drive and the “place” icon means that the test is done on location
-    It is possible to mark a COVID-19 test center as a favorite. The information is stored is the shared preferences of the Android OS.

---
### Authors' quote

> G/ Tu pourrais le push ?

> V/ Par contre niveau UI mon activité est dégueulasse, c'est froid et impersonnel

> V/ La réduction des données est plus faible que prévue et ça crash toujours

> G/ La solution de facilité serait de réduire le JSON...

> V/ Déjà si on a une base solide, c'est cool !

> V/ Bon du coup, j'ouvre Android studio. Tu en étais ou toi ?

> G/ Bizard normalement, on a le même code, chez moi ça marche...

> G/ Bingo !

> V/ Mais j'habite pas dans le CNRS moi, aled.

### License

MIT