package io.codekaffe.ifood.cadastro.resources;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import io.codekaffe.ifood.cadastro.CadastroTestLifeCycleManager;
import io.codekaffee.ifood.cadastro.repositories.LocalizacaoRepository;
import io.codekaffee.ifood.cadastro.repositories.PratoRepository;
import io.codekaffee.ifood.cadastro.repositories.RestauranteRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(value = CadastroTestLifeCycleManager.class)
public class RestaurantResourceTest {


    @Inject
    RestauranteRepository restauranteRepository;


    @Inject
    PratoRepository pratoRepository;

    @Inject
    LocalizacaoRepository localizacaoRepository;

    @Test
    @DataSet(value = "restaurants-cenario.yml")
    public void testBuscarRestaurantes() {
        var resultado = given()
                .when().get("/restaurantes")
                .then()
                .statusCode(200)
                .extract().response();


        Assertions.assertNotNull(resultado.getBody());

    }


    @Test
    @DataSet(value = "restaurants-cenario.yml")
    @DisplayName(value = "Deve retornar um restaurante cadastrado e o status 200")
    public void  testBuscarRestaurantePorId(){
        Long parameterID = 123L;
        var resultado =  given()
                .with().pathParams("id", parameterID)
                .when().get("/restaurantes/{id}")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("nome", not(is("")))
                .body("id", is(parameterID.intValue()))
                .extract().response();


        Assertions.assertNotNull(resultado.getBody());

    }
}
