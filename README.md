# dojo test

## Considerations:

Android version: minSdk 15 target 23
I've set minimum platform for the app the same one used buy LoLSumo at Play Store.
Language: Java:
I'm aware that the Dojo app is written using Kotlin, but i wrote in Java since i'm more familiar with it. 
I've already written this down so i will start studying Kotlin to get used to it.

There are some things that i've done in the project that if had the time i would like to 
test it better on different scenarios and also check if it is really necessary  to work that
way into the app and i would like go through some of them.


**Card Drag Indicator**
I've created a custom component used in the main screen that you can drag the card and then
it opens the match list activity. The component i have test in my Nexus 4 and my girlfriends Moto G
and have work as expected, i haven't had a chance to test in different resolutions and to assure better compatibility
that would be necessary. Also after some research i've found this library (https://github.com/umano/AndroidSlidingUpPanel)
which may be used to the card dragging but really making it ocuppy the whole screen. Also, a simpler use could be just an image
to pass the intention that there cards to be seen.

**Custom Rating Bar**
I've customized the Android RatingBar for the match details info, since i didn't have different images for the different resolutions
(mdhdpi, xhdpi, ...) i've used a fixed drawable to represent each item and i'm aware that the rating bar don't adjust to the screen
so if tried devices with resolution smaller thant the ones i've tested it may look buggy with the steps not being properly set.

**Horizontal Badge Scroll Inside Cards**
I've used a ViewPager inside each card item so you can add more than one throphys to be shown, like i've seen in some screen 
of the app. I'm not sure if memory-wise this would be the best option, it is working as expected but sometimes when you scroll too fast
it jumps to the next item, looking a bit odd. I think that you could achieve the same behaviour using a horizontal recycler view
and customizing it so it shows only an item at a time, i didn't had the time to implement/test it so i stayed with the ViewPager.

**Synchronization / Domain**
I've created a fake RestApi to do the "queries" for me and map the Json nto DTO's. I'm note sure if the real API could respond
the way i designed, like querying specific details based in the match id. Even if it can't i could create a local cache of the
query and used it locally, kind the way i did it.

Also i haven't created a specific domain / services / data mappers / repositories, since this were a small example and the 
Rest endpoint/business rules weren't very clear and for that i didn't think that it would be necessary. So for now its working 
based on the DTO's. I'm aware that in a larger application you should have a Domain (like i usually do)  with the business rules and also avoiding 
anemic models.

**Data**
I have change the Json a bit to contemplate more scenarios, i've changed the date so we can have a card from yesterday and 
also one from august to be displyed the divisions. I wasn't sure if i need to query to the server again when reached the 
end of the list so i could query the matches related to that period, but for now i left like that.


**Libraries**
Dagger 2 - Dependency Injection (Injecting the view presenters / rest api / context)
ButterKnife - View Injection
RxJava - Using for querying / async processing