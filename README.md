# Les Notifications

## But du TD
Tu va mettre en place une notification liée au lecteur audio que tu auras préalablement écrit. Cette notification sera particulière car elle permettra de contrôler la lecture de la chanson à l'aide des boutons PLAY, PAUSE et STOP.

* Tu vas devoir intégrer les *Notifications* à ton projet MediaPlayer.
* Tu peux reprendre ton MediaPlayer ou alors tu peux récupérer une version [ici](https://github.com/WildCodeSchool/dojo-android-audio-manager).

## Etapes
### Détecter que l’application est en foreground
Pour ce faire, il faut d’abord récupérer l’information comme quoi l’application est en premier plan.
Pour cela, tu peux lire le schéma d’état de la classe Activity se trouvant sous ce [lien](https://developer.android.com/reference/android/app/Activity).

### Créer la notification

Lorsque l'application est en *foreground*, affiche une [notification](https://developer.android.com/guide/topics/ui/notifiers/notifications) contenant :
* Le nom de la chanson en cours.
* Un bouton Play
* Un bouton Pause
* Un bouton Stop

Pour créer la notification, tu utiliseras un [NotificationCompat.Builder](https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder).

### Interagir avec l'activité
Les boutons de la notification doivent interagir avec le média en cours. Chacun des boutons doit avoir un [Intent](https://developer.android.com/reference/android/content/Intent) qui lui est associé. Pour intercepter l’*intent* du bouton cliqué et le rediriger vers l'activité, tu vas utiliser un [BroadcastReceiver](https://developer.android.com/reference/android/content/BroadcastReceiver).

### BroadcastReceiver et LocalBroadcastManager
* Tu vas créer une classe qui hérite de BroadcastReceiver qui va intercepter les événements de la notification.
* Ensuite, tu vas créer un autre BroadcastReceiver mais cette fois-ci directement dans l'activité. Ceci permettra d'interagir avec le lecteur audio. On dira que ce *Receiver* est local à notre activité.
 * Finalement, tu va faire discuter les deux BroadcastReceiver à l'aide de la classe [LocalBroadcastManager](https://developer.android.com/reference/android/support/v4/content/LocalBroadcastManager).

## Documentation
### Notifications
* [notifications](https://developer.android.com/guide/topics/ui/notifiers/notifications)
* [build-notification](https://developer.android.com/training/notify-user/build-notification)
* [expanded](https://developer.android.com/training/notify-user/expanded)

### BroadcastReceiver/LocalBroadcastManager
* [Broadcasts overview](https://developer.android.com/guide/components/broadcasts)
* [BroadcastReceiver](https://developer.android.com/reference/android/content/BroadcastReceiver)
* [LocalBroadcastManager](https://developer.android.com/reference/android/support/v4/content/LocalBroadcastManager)

## TD suivant
* [Service](https://github.com/boutin-k/dojo-android-audio-04-service)
