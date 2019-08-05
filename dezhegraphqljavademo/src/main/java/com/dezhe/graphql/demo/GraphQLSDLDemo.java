package com.dezhe.graphql.demo;

import com.dezhe.graphql.domain.Card;
import com.dezhe.graphql.domain.User;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * @Author dezhe
 * @Date 2019/8/3 11:59
 */
public class GraphQLSDLDemo {

    public static void main(String[] args) throws IOException {

        //读取数据
        String filename = "user.graphqls";

        String fileContent = IOUtils.toString(GraphQLSDLDemo.class.getClassLoader().getResource(filename),"UTF-8");

        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(fileContent);

        //解决的是数据的查询
        RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
                .type("UserQuery", a-> a.dataFetcher(
                        "user",s ->{
                            Long id = s.getArgument("id");
                            Card card = new Card("123456789", id);
                            return new User(id,"张三"+id,20+id.intValue(),card);}
                ))
                .build();

        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);

        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();

        String query = "{user(id:200){id,name,age,card{cardNumber,userId}}}";

        ExecutionResult result = graphQL.execute(query);

        System.out.println("user:"+query);

        //官方标准的输出
        System.out.println(result.toSpecification());

    }

}
