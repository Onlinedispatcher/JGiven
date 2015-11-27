package com.tngtech.jgiven.junit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.tngtech.jgiven.junit.test.GivenTestStep;
import com.tngtech.jgiven.junit.test.ThenTestStep;
import com.tngtech.jgiven.junit.test.WhenTestStep;
import com.tngtech.jgiven.report.model.Word;

public class DataTableTest extends ScenarioTest<GivenTestStep, WhenTestStep, ThenTestStep> {

    @Test
    public void test_data_table_arguments() throws Throwable {
        given().the_following_data(
            new GivenTestStep.CoffeePrice( "Espresso", 1.5 ),
            new GivenTestStep.CoffeePrice( "Cappuccino", 2.5 ) )
            .and().some_boolean_value( true );
        when().something();
        then().something();

        getScenario().finished();

        Word lastWord = getScenario().getScenarioCaseModel().getFirstStep().getLastWord();
        List<List<String>> tableValue = lastWord.getArgumentInfo().getDataTable().getData();
        assertThat( tableValue ).isNotNull();
        assertThat( tableValue.get( 0 ) ).containsExactly( "name", "price in EUR" );
        assertThat( tableValue.get( 1 ) ).containsExactly( "Espresso", "1.5" );
        assertThat( tableValue.get( 2 ) ).containsExactly( "Cappuccino", "2.5" );

    }

    @Test
    public void test_custom_table_formatter() throws Throwable {
        given().a_list_of_PoJos_with_custom_table_formatter(
            new GivenTestStep.CoffeePrice( "Espresso", 1.5 ),
            new GivenTestStep.CoffeePrice( "Cappuccino", 2.5 ) )
            .and().some_boolean_value( true );

        getScenario().finished();

        Word lastWord = getScenario().getScenarioCaseModel().getFirstStep().getLastWord();
        List<List<String>> tableValue = lastWord.getArgumentInfo().getDataTable().getData();
        assertThat( tableValue ).isNotNull();
        assertThat( tableValue.get( 0 ) ).containsExactly( "coffeePrices" );
        assertThat( tableValue.get( 1 ) ).containsExactly( "Espresso: 1.5" );
        assertThat( tableValue.get( 2 ) ).containsExactly( "Cappuccino: 2.5" );

    }
}
