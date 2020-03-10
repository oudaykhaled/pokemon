# Pokemon App - Sample App showing how to refactor an app progressively

Sample application shows the different kind of pokemons.
This project is made in MVVMi patterns and follows the Clean Architecture Book written by Robert Martin.

# Overall Architecture
 
 1- View (Activities, Fragments, Views ...): Manage the UI according to its ViewModel
 2- ViewModel: Connect Views to one or more use cases.
 3- Model: Data transmitted accross all architectre components
 4- Usecases: hold the business rules
 5- Repository: Manage Data sources

# Tech

    1-  Kotlin
    2-  ViewModel with Live Data.
    3-  Coroutines
    4-  Dagger 2
    5-  RxJava/RxKotlin
    6-  Retrofit
    7-  JUnit
    8-  Mochito
    
### Documentation
Documentation for the public Pokémon REST API can be found here: https://pokeapi.co
