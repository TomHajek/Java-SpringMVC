Introduction to THYMELEAF:
---

- Thymeleaf is server-side templating engine
- thymeleaf expressions are used and injected with model data in our html tags, for example:
   - th:text="${name}"
   - th:id="|container-${...}|"
   - th:unless="${group.name > 0}|"


- expressions:
   - th:if ................ if condition
   - th:each .............. for each loop
   - th:text .............. adds model(object) text property
   - th:id ................ adds model(object) id
   - th:object ............ represents actual model
   - th:src ............... adds data source
   - th:action ............ to submit something (do the action), usually post endpoint for submitting the form


- selection expressions:
   - th:text="*{firstName}" .............. you don't have to type in user.firstName


- message expressions:
   - th:text="#{welcome.text}" ........... allows you to quickly get text from application.properties, or you can create dedicated message.properties file


- link expressions:
   - th:href="@{/users/{userId}/edit}" ... adds link/url


- fragments == small reusable html pieces like headers, footers
   - th:fragment="separator"


- insert & replace == are used especially for fragments

   - //.........................(folder :: name)
   - th:insert  = "~{fragments :: separator}"
   - th:replace = "fragments :: separator"


- page security:
  - sec:authorize

____