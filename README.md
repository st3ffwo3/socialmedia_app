Sichere Systeme - Secure Social Media App
====================================

Dieses Repository beinhaltet alle Projekte der Secure Social Media App.

Voraussetzungen: 
----------------
* JDK 1.6
* Eclipse Indigo SR2
* Checkstyle Plugin
* JBoss Tools Plugin
* EGit Plugin
* JBoss Application Server 6.1
* MySQL 5.5

Build Properties:
-----------------	
Für die ant Skripten existieren global-build.properties im Projekt "SecureSocialMediaAppBuildEnv". Diese müssen individuell auf jedem Rechner, je nach Konfiguration angepasst werden. Speziell der Installationspfad des JBoss Application Server muss richtig gesetzt werden, da sonst der Build-Prozess fehlschlägt.

Es besteht die Möglichkeit die eingecheckten global-build.properties im Projekt "SecureSocialMediaAppBuildEnv" zu überschreiben. Hierzu legt man eigene global-build.properties ins Verzeichnis <USER_HOME>/.ant/global-build.properties. Diese werden als erstes beim Build angezogen.

Build:
------
Die Secure Social Media App lässt sich per ant bauen. Das zugehörige Skript liegt im Projekt "SecureSocialMediaApp".

    ant -buildfile SecureSocialMediaApp/build.xml complete-build

Deployment:
-----------
Für das automatische Deployment ist ebenfalls ein ant Skript vorhanden.

    ant -buildfile SecureSocialMediaApp/dev-build.xml deploy
