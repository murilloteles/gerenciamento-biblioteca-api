<h1 align="center">gerenciamento-biblioteca-api</h1>
<p align="center">Api Rest para gerenciar uma biblioteca.
</p>

# Funcionalidades do projeto

- A API possuí operações para realizar cadastro, consulta e alterações de dados de Funcionários e Clientes. Consultar Endereços já cadastrados e podendo também consultar o CEP que é mais comum entre os funcionários.

-  A Documentação das Funcionalidades pode ser acessada pelo link: `http://localhost:8080/swagger-ui/`.

# Tecnologias utilizadas

- `Java 8`
- `Spring Boot`
- `Hibernate`
- `PostgreSQL`
- `JUnit`

# Dependências do projeto

- `Api ViaCEP`: Por meio de requisição GET para o endereço "https://viacep.com.br/ws/{cep}/json", utiliza-se essa funcionalidade para buscar dados de endereço de um CEP específico. 
