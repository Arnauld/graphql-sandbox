package assetmngt.graphql;

import assetmngt.core.InstrumentsFake;
import assetmngt.core.ModelsFake;
import assetmngt.core.QuotationsFake;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import graphql.ExecutionResult;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GraphQLEngineTest {
    private static final Logger LOG = LoggerFactory.getLogger(GraphQLEngineTest.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private GraphQLEngine engine;

    @Before
    public void init() {
        engine = new GraphQLEngine(new ModelsFake(), new InstrumentsFake(), new QuotationsFake());
    }

    @Test
    public void query_simple_case() throws IOException {

        ExecutionResult executionResult = engine.execute(stripMargin("" +
                "| query {                             " +
                "|   model(code: 'MODELEGCCCPASV') {   " +
                "|     code                            " +
                "|     label                           " +
                "|   }                                 " +
                "| }                                   "));

        assertThat(executionResult.getErrors()).isEmpty();

        Map<String, Object> data = executionResult.getData();
        LOG.info("Execution result: \n{}", GSON.toJson(data));

        assertThat(data).containsKey("model");
    }

    @Test
    public void query_model_fields() throws IOException {

        ExecutionResult executionResult = engine.execute(stripMargin("" +
                "| query {                             " +
                "|   model(code: 'MODELEGCCCPASV') {   " +
                "|     code                            " +
                "|     label                           " +
                "|     nature {                        " +
                "|       code                          " +
                "|     }                               " +
                "|     structure {                     " +
                "|       code                          " +
                "|       label                         " +
                "|     }                               " +
                "|     use {                           " +
                "|       code                          " +
                "|       label                         " +
                "|     }                               " +
                "|     active                          " +
                "|   }                                 " +
                "| }                                   "));

        assertThat(executionResult.getErrors()).isEmpty();

        Map<String, Object> data = executionResult.getData();
        LOG.info("Execution result: \n{}", GSON.toJson(data));

        assertThat(data).containsKey("model");
    }


    @Test
    public void query_model_holdings() throws IOException {

        ExecutionResult executionResult = engine.execute(stripMargin("" +
                "| query {                                        " +
                "|   model(code: 'MODELEGCCCPASV') {              " +
                "|     code                                       " +
                "|     label                                      " +
                "|     active                                     " +
                "|     holdings(paging:{after:'AZ2', count:50}) { " +
                "|       pageInfos {                              " +
                "|         after                                  " +
                "|         count                                  " +
                "|       }                                        " +
                "|       holdings {                               " +
                "|         weight                                 " +
                "|         qty                                    " +
                "|         quotation {                            " +
                "|           value                                " +
                "|           currency                             " +
                "|         }                                      " +
                "|         instrument {                           " +
                "|           isin                                 " +
                "|         }                                      " +
                "|       }                                        " +
                "|     }                                          " +
                "|   }                                            " +
                "| }                                              "));

        assertThat(executionResult.getErrors()).isEmpty();

        Map<String, Object> data = executionResult.getData();
        LOG.info("Execution result: \n{}", GSON.toJson(data));

        assertThat(data).containsKey("model");
    }


    private static String stripMargin(String s) {
        return s.replace("|", "\n").replace('\'', '"');
    }
}
