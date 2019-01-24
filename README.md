# LES NOTIFICATIONS

## But du TD
Tu va mettre en place une notification liée au lecteur audio que tu auras préalablement écrit.
Ou alors tu peux récupérer une version du lecteur audio sous ce [repo](https://github.com/WildCodeSchool/dojo-android-audio)

## Etapes
### Détecter que l’application est en background
Pour ce faire, il faut d’abord récupérer l’information comme quoi l’application est en arrière plan.
Pour cela, tu peux lire le schéma d’état de la classe Activity se trouvant sous ce [lien](https://developer.android.com/reference/android/app/Activity)

### Créer la notification

Lorsque c’est le cas, afficher une notification contenant :
* Le nom de la chanson en cours.
* Un bouton Play
* Un bouton Pause
* Un bouton Stop

Les boutons doivent interagir avec le média en cours.
Vous utiliserez un *NotificationCompat.Builder*

## Documentation
* [notifications](https://developer.android.com/guide/topics/ui/notifiers/notifications)
* [build-notification](https://developer.android.com/training/notify-user/build-notification)
* [expanded](https://developer.android.com/training/notify-user/expanded)

En français :
* [notifications](https://developer.android.com/distribute/best-practices/engage/rich-notifications?hl=fr)
