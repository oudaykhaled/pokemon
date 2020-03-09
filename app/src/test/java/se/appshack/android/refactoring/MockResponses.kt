package se.appshack.android.refactoring

import se.appshack.android.refactoring.main.data.model.*
import se.appshack.android.refactoring.main.data.model.response.PokemonDetailsResponse
import se.appshack.android.refactoring.main.data.model.response.PokemonSpeciesResponse


fun buildEmptyPokemonSpeciesResponse(): PokemonSpeciesResponse? {
    return PokemonSpeciesResponse(
        0,
        0,
        Color("",""),
        ArrayList(),
        EvolutionChain(""),
        EvolvesFromSpecies("",""),
        ArrayList(),
        ArrayList(),
        false,
        0,
        ArrayList(),
        Generation("",""),
        GrowthRate("",""),
        Habitat("",""),
        false,
        0,
        0,
        false,
        "",
        ArrayList(),
        0,
        ArrayList(),
        ArrayList(),
        Shape("",""),
        ArrayList()
    )
}


fun buildEmptyPokemonDetailsResponse(): PokemonDetailsResponse? {
    return PokemonDetailsResponse(
        ArrayList(),
        0,
        ArrayList(),
        ArrayList(),
        0,
        ArrayList(),
        0,
        false,
        "",
        ArrayList(),
        "",
        0,
        Species("",""),
        Sprites("","","","","","","",""),
        ArrayList(),
        ArrayList(),
        0)
}