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
import org.approvaltests.JsonApprovals;
import org.junit.jupiter.api.Assertions;
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
    @DataSet(value = "restaurants-cenario.yml", cleanAfter = true)
    public void testBuscarRestaurantes() {
        var resultado = given()
                .when().get("/restaurantes")
                .then()
                .statusCode(200)
                .extract().response();


        Assertions.assertNotNull(resultado.getBody());




    }
}
