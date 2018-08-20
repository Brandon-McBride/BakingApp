package com.mcbridebrandon.bakingapp.utilities;

import com.mcbridebrandon.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.mcbridebrandon.bakingapp.utilities.NetworkConfig.FILENAME;

public interface NetworkService {
    @GET(FILENAME)
    Call<List<Recipe>> getRecipes();
}
