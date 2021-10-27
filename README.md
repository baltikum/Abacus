# Abacus
Agile Programming School Project (DAT257-DIT257-agile-software-project-management)

- An Android App displaying airpollutant values in the city of Gothenburg.


https://user-images.githubusercontent.com/26551674/139029232-b07be213-2e58-421d-aef8-cd47d89f8631.mp4


## Who we are:

baltikum - Mattias Davidsson<br>
ErikLinderGit - Erik Linder<br>
kevinph00 - Kevin Pham<br>
kossamu - Johnny Larsson<br>
Lukvargren - Lukas Magnusson<br>
vicboq - Victoria Boquist<br>

All attending TIDAL-03, Computer Engineering at Chalmers Technical Univeristy of Gothenburg.

## Navigating the repository

Source code located in app/src/main/java/com/example/luftkvalitet/

It is divided in three parts, the user-interface, networking classes, and overview that simplified connects backend together.

## Trello
The projects scrumboard was made and used in Trello and it can be found here https://trello.com/b/oBP6vXgw/abacus


## Gitinfo
Johnny<br>
I worked mostly on the UI and started with making the three tabs for the application.
On the start page we were multiple people that each worked by themself to make a button that updates textViews with different values, 
based on station. We then changed that to a spinner and again had multiple people working on the spinner by themself to select the station instead. 
I also researched low and high values with Victoria for the different sensors and fixed them so that the values got different colors based on the value.
After that, I worked a lot with Victoria on the statistics page, where we fixed a bar graph that connects to the API.
We fixed things like color coding for different values, how the X and Y axis behave, and similar things. We worked with Mattias to implement so we could
show either the last day(24 values) or the last week with the average value for the day. Then we did a lot of UI fixed where I added another constraint
so that the buttons that are in a row are centered, changed the theme color of the app, and that the graph resizes correctly when changing from last day
to last week. <br>
<br>

Erik<br>
Overall, my work mostly focused on map related issues. <br>
- Started out creating a base design with three tabs and six clickable buttons (Erik and Johnny had the same assignment since it was difficult to split up work at this stage and to give the team alternatives to choose between. The two apps came out fairly identical but moving forward the team based the work on Johnny's template.)<br>
- Looked into different OSM alternatives, especially MapBox which at first glance seemed like a good alternative. But could not get it functioning despite much effort. Moved on to successfully use Google Maps to create another mockup app, which seemed overall easier to use compared to MapBox for the app we were designing. The team decided to use Google Maps moving forward.<br>
- Co-worked mostly with Lukas to implement the final version using Google Maps into the tabbed fragment app, creating functions like markers and popup toasts when clicking on the markers, displaying the values for different stations.<br>
<br>


