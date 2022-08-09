package com.kev.cocktailsdb.util

import com.google.gson.*
import com.kev.cocktailsdb.model.CocktailsResponse
import java.lang.reflect.Type

class Custom : JsonDeserializer<CocktailsResponse>{
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): CocktailsResponse? {
        val cocktailsResponse : CocktailsResponse? = null
        val jsonArray : JsonArray = json.asJsonArray

        if (jsonArray.size() !=1){
            throw IllegalArgumentException("Unexpected Json")
        }

        val jObject : JsonObject = jsonArray.get(0).asJsonObject
        return  cocktailsResponse
    }

}

//Gson gson = new GsonBuilder().registerTypeAdapter(NestedPojo.class, new Custom()).create();
