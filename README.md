## JavaScript undefine, null
https://dorey.github.io/JavaScript-Equality-Table/

## Regx Visual
https://regexper.com/

## Regx
https://regexr.com/

## Thymeleaf
/*[+ ${msg} +]*/

### thymeleaf-extras-springsecurity
     https://github.com/thymeleaf/thymeleaf-extras-springsecurity

xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/extras/spring-security"

dependencies {
  implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity5'
}

CREATE TABLE persistent_logins (
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used timestamp not null,
    PRIMARY KEY (series)
);

