# Use uma imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho
WORKDIR /pagamento

# Copie o arquivo JAR para a imagem
COPY target/pagamento-0.0.1-SNAPSHOT.jar app.jar

# Defina o comando padrão para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

# Exponha a porta em que a aplicação vai rodar
EXPOSE 8080