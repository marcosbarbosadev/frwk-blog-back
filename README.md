# Blog API

#### Requisitos
- Maven
- Java 8
- PostgreSQL (no projeto foi utilizada a versão 12.1)

#### Instalar e configurar o Maven (passos para ambiente linux)
- Baixar o Manven em https://maven.apache.org/download.cgi. Pacote "apache-maven-3.6.3-bin.tar.gz"
- Descompactar para a pasta onde será instalado. Ex.: /opt/apache-maven-3.6.3
- Configurar a variável de ambiente M2_HOME e adicionar o caminho do maven ao PATH do sistema, incluindo as 2 linhas abaixo no arquivo ~/.bashrc
    
    $ export M2_HOME=/opt/apache-maven-3.6.3
    
    $ PATH=$PATH:$M2_HOME/bin
    
- Carregue as novas configurações

    $ source ~/.bashrc

#### Fazer o clone do projeto:

    $ git clone https://github.com/marcosbarbosadev/frwk-blog-back.git
    
#### Alterar configurações no arquivo "aplication.properties" conforme seu ambiente
- Alterar url de conexão, usuário e senha
- Manter a linha "spring.jpa.hibernate.ddl-auto=update" com valor "upate" para que a estrutura de tabelas seja criada ao levantar a aplicação
- Configurar o caminho da pasta de upload de fotos, propriedade "blog.upload_dir" (Informar caminho absoluto)

#### Demais Configurações
- Criar Schema "frwk_blog" no PostgreSQL
- Rodar o comando a seguir, no terminal (pode demorar um pouco na primeira execução, porque o maven irá baixar todas as dependências)
    
    $ mvn spring-boot:run

- Após iniciar o projeto, execute o seguinte script para criar o primeiro usuário:

INSERT INTO frwk_blog.users (name, email, password, created_at, updated_at) VALUES ('Admin', 'admin@mail.com', '$2a$10$hWTMuPlYWJhVzlc7HrAeh.WuinWYRfbVwkG/Ul8D529KOZcmO4HaO', now(), now());
- Para acessar a aplicação use o login "admin@mail.com" e a senha "dev"

Obs.: Configurei o swagger, mas infelizmente eu não consegui fazer funcionar o acesso dos endpoints protegidos pela interface dele. Então só é possível usar ele para logar e os outros endpoints precisam ser acessados de um outro rest console.
