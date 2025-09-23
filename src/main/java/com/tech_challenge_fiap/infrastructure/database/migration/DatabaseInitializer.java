package com.tech_challenge_fiap.infrastructure.database.migration;

import com.tech_challenge_fiap.data.models.ProductDataModel;
import com.tech_challenge_fiap.utils.enums.CategoryEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;

@Slf4j
@Component
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initializeData(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        return args -> {
            try {
                initializeProducts(dynamoDbEnhancedClient);
            } catch (Exception e) {
                log.error("Erro ao inicializar dados no DynamoDB", e);
            }
        };
    }

    private void initializeProducts(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        DynamoDbTable<ProductDataModel> productTable = dynamoDbEnhancedClient.table(
            "tech-challenge-products", 
            TableSchema.fromBean(ProductDataModel.class)
        );

        long productCount = productTable.scan(ScanEnhancedRequest.builder().build())
            .items()
            .stream()
            .count();

        if (productCount == 0) {
            log.info("Inicializando dados de produtos no DynamoDB...");

            productTable.putItem(createProduct(
                "Hambúrguer Artesanal",
                "Hambúrguer com carne 180g, queijo cheddar e pão brioche",
                "https://br.freepik.com/fotos-gratis/hamburguer-de-vista-frontal-em-um-carrinho_9523079.htm#fromView=search&page=1&position=2&uuid=9ecc3827-6e81-4f85-bcac-0728fe83ba6f&query=hamburguer+artesanal",
                25L,
                null,
                CategoryEnum.LANCHE,
                30L));

            productTable.putItem(createProduct(
                "Porção de Batata Frita",
                "Porção média de batata frita crocante",
                "https://br.freepik.com/fotos-gratis/vista-superior-deliciosas-batatas-fritas-e-molho_21745017.htm#fromView=search&page=1&position=3&uuid=9af82396-46ee-477d-9c07-d65a18279f85&query=por%C3%A7%C3%A3o+de+batata+frita",
                1500L,
                1350L,
                CategoryEnum.ACOMPANHAMENTO,
                40L));

            productTable.putItem(createProduct(
                "Refrigerante Lata",
                "Lata 350ml de refrigerante (diversos sabores)",
                "https://br.freepik.com/imagem-ia-gratis/vista-horizontal-de-uma-maquete-de-uma-lata-de-bebidas-gaseificadas_126949086.htm#fromView=search&page=1&position=10&uuid=c2536a03-1c39-4d59-80bb-dfbd714e6c31&query=refrigerante+lata",
                700L,
                500L,
                CategoryEnum.BEBIDA,
                40L));

            productTable.putItem(createProduct(
                "Brownie com Sorvete",
                "Brownie de chocolate com bola de sorvete de creme",
                "https://br.freepik.com/fotos-gratis/brownie-de-chocolate-servido-com-sorvete-de-baunilha-e-morangos_5536627.htm#fromView=search&page=1&position=6&uuid=7e11a676-378e-48f6-946d-18d10db9592f&query=Brownie+com+Sorvete",
                1700L,
                null,
                CategoryEnum.SOBREMESA,
                25L));
        }
    }

    private ProductDataModel createProduct(String name, String description, String image,
                    Long price, Long priceForClient,
                    CategoryEnum category, Long quantity) {
        return ProductDataModel.builder()
                .name(name)
                .description(description)
                .image(image)
                .price(price)
                .priceForClient(priceForClient)
                .category(category)
                .quantity(quantity)
                .hasPromotion(priceForClient != null ? "true" : "false")
                .build();
    }
}