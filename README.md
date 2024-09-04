# PAGAMENTO API
Este projeto implementa uma API REST para gerenciamento de contas usando Spring Boot.

# Características
    Gerenciamento de contas:
    Crie novas contas.
    Recuperar detalhes da conta.
    Atualize as informações da conta.
    Gerenciamento de usuários:
    Cadastro de usuário.
    Autenticação do usuário.
# Segurança:
    Autenticação JWT.
    Configuração do CORS.
# Tecnologias
    Spring Boot: Framework para construção de APIs REST.
    Spring Data JPA: Mapeamento Objeto-Relacional (ORM) para interações de banco de dados.
    Spring Security: Estrutura para proteger a API.
    PostgreSQL: banco de dados na memória para fins de desenvolvimento.

# Estrutura do Projeto
O projeto segue uma estrutura padrão do Spring Boot:

# src/main/java: Contém a lógica principal do aplicativo:
    com.pagamento: Pacote contendo a aplicação principal, controladores, serviços, repositórios e classes de modelo.
    com.pagamento.domain.model: Representa as entidades da aplicação (ex.: Conta, Usuário).
    com.pagamento.domain.service: Implementa a lógica de negócio para gerenciamento de contas e usuários.
    com.pagamento.domain.user.dto: Contém objetos de transferência de dados (DTOs) para operações relacionadas ao usuário.
    com.pagamento.infra.security: Trata de aspectos de autenticação e segurança.
    com.pagamento.repository: Define repositórios para acesso aos dados do banco de dados.
    com.pagamento.web.controller: Fornece endpoints REST para interações de API.
    src/main/resources: Contém arquivos de configuração e outros recursos:
    application.properties: definições de configuração do aplicativo.
    db/migration: Scripts para migrações de banco de dados.
    modelos: modelos para renderizar respostas.
    src/test/java: Contém testes de unidade para o aplicativo.
    src/test/resources: contém dados de teste e arquivos de configuração.
