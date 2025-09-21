package com.tech_challenge_fiap.infrastructure.database.migration;

import com.tech_challenge_fiap.data.models.ProductDataModel;
import com.tech_challenge_fiap.utils.enums.CategoryEnum;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(order = "001", id = "insert-products", author = "11SOAT")
public class ProductsInsertDatabaseMigration {

    @RollbackExecution
    public void rollbackExecutionMethodName() {
        System.out.println("rollback execution for tech-chall database...");

    }

    @Execution
    public void insertProducts(MongoTemplate mongoTemplate) {
        mongoTemplate.save(
                new ProductDataModel(
                        null,
                        "Hambúrguer Artesanal",
                        "Hambúrguer com carne 180g, queijo cheddar e pão brioche",
                        "https://br.freepik.com/fotos-gratis/hamburguer-de-vista-frontal-em-um-carrinho_9523079.htm#fromView=search&page=1&position=2&uuid=9ecc3827-6e81-4f85-bcac-0728fe83ba6f&query=hamburguer+artesanal",
                        25L,
                        null,
                        CategoryEnum.LANCHE,
                        30L)
        );
        mongoTemplate.save(
                new ProductDataModel(
                        null,
                        "Porção de Batata Frita",
                        "Porção média de batata frita crocante",
                        "https://br.freepik.com/fotos-gratis/vista-superior-deliciosas-batatas-fritas-e-molho_21745017.htm#fromView=search&page=1&position=3&uuid=9af82396-46ee-477d-9c07-d65a18279f85&query=por%C3%A7%C3%A3o+de+batata+frita",
                        1500L,
                        1350L,
                        CategoryEnum.ACOMPANHAMENTO,
                        40L)
        );
        mongoTemplate.save(
                new ProductDataModel(
                        null,
                        "Refrigerante Lata",
                        "Lata 350ml de refrigerante (diversos sabores)",
                        "https://br.freepik.com/imagem-ia-gratis/vista-horizontal-de-uma-maquete-de-uma-lata-de-bebidas-gaseificadas_126949086.htm#fromView=search&page=1&position=10&uuid=c2536a03-1c39-4d59-80bb-dfbd714e6c31&query=refrigerante+lata",
                        700L,
                        500L,
                        CategoryEnum.BEBIDA,
                        100L)
        );
        mongoTemplate.save(
                new ProductDataModel(
                        null,
                        "Brownie com Sorvete",
                        "Brownie de chocolate com bola de sorvete de creme",
                        "https://br.freepik.com/fotos-gratis/brownie-de-chocolate-servido-com-sorvete-de-baunilha-e-morangos_5536627.htm#fromView=search&page=1&position=6&uuid=7e11a676-378e-48f6-946d-18d10db9592f&query=Brownie+com+Sorvete",
                        1700L,
                        null,
                        CategoryEnum.SOBREMESA,
                        25L)
        );
    }
}
 