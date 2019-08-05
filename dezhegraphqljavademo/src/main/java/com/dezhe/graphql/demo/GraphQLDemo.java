package com.dezhe.graphql.demo;

import com.dezhe.graphql.domain.User;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

import static graphql.Scalars.*;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

/**
 * @Author dezhe
 * @Date 2019/8/3 9:56
 */
public class GraphQLDemo {

    public static void main(String[] args) {

        /**
         * #定义对象 中的属性
         */
        GraphQLObjectType graphQLObjectType = GraphQLObjectType.newObject().name("User").
                field(newFieldDefinition().name("id").type(GraphQLLong)).
                field(newFieldDefinition().name("name").type(GraphQLString)).
                field(newFieldDefinition().name("age").type(GraphQLInt))
                .build();

        /**
         *指定对象以及参数类型 user 类型
         */
        GraphQLFieldDefinition FieldDefinition = newFieldDefinition().name("user")
                .argument(GraphQLArgument.newArgument().name("id").type(GraphQLLong).build())
                .dataFetcher(s ->{
                    Long id = s.getArgument("id");
                    return new User(id,"张三"+id,20+id.intValue(),null);
                })
                //.dataFetcher(new StaticDataFetcher(new User(1L,"张三",20)))
                .type(graphQLObjectType).build();

        /**
         *#定义查询的类型
         */
        GraphQLObjectType userQuery = GraphQLObjectType.newObject().name("UserQuery").field(FieldDefinition).build();

        /**
         *#定义查询  约束
         */
        GraphQLSchema graphQLSchema = GraphQLSchema.newSchema().query(userQuery).build();

        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();

        String query = "{user(id:200){id,name,age,card{cardNumber,userId}}}";

        ExecutionResult result = graphQL.execute(query);

        System.out.println("user:"+query);

       /* System.out.println(result.getErrors());

        System.out.println(result.getData());*/

        //官方标准的输出
        System.out.println(result.toSpecification());

    }

}
