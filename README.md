Dzień dobry
Z tej strony Adam z Atipera, jestem właścicielem softwarehouse i osobą, z którą Pan/Pani potencjalnie będzie pracować w projekcie. Jeżeli są jakieś pytania, proszę pisać w odpowiedz na maila, chętnie odpowiem. Aby zachować uczciwość rekrutacji, nie odpisujemy na pytania odnośnie treści zadania.

Wysyłam zdania rekrutacyjne. Proszę je zrobić w Java 21/Kotlin + Spring Boot 3/Quarkus 3, w tym w czym wygodniej.

Zadanie nie ma określonego czasu wykonania. Prowadzimy rekrutację do momentu aż zapełnimy wszystkie wolne miejsca. Pierwsze spotkania będę umawiał 05.08.2024 na podstawie otrzymanych do tego czasu zadań. Wymogiem jest możliwość rozpoczęcia pracy najpóźniej 01.10.2024.

W przypadku wysłania zadania i nie przejścia do kolejnego etapu oferuję 10 minutą rozmową telefoniczna wyjaśniającą czego zabrakło (dla numerów zagranicznych zadzwownie na WhatsApp).

Rozwiązanie zadania wysyłamy jako link do publicznego repo na github.

Powodzenia
Adam Popławski

Acceptance criteria:
As an api consumer, given username and header “Accept: application/json”, I would like to list all his github repositories, which are not forks. Information, which I require in the response, is:

Repository Name
Owner Login
For each branch it’s name and last commit sha

As an api consumer, given not existing github user, I would like to receive 404 response in such a format:
{
    “status”: ${responseCode}
    “message”: ${whyHasItHappened}
}

Notes:
Please full-fill the given acceptance criteria, delivering us your best code compliant with industry standards.
Please use developer.github.com/v3 as a backing API
Application should have a proper README.md file

-----------------------------------------------------------------------
что я должен сделать:
1) расписать входы и выходы 
2) настроить openfeign 
3) обработать все виды ощибок github 
4) настроить так что если запрос не удался то обратится по ссылке другой (cirkuit breaker)
5) покрыть код тестами не менее 80 процентов
6) подключить checkstyle и sonarqube 
7) swagger документация
8) написать github actions пайплайн
8) задеплоить этот сервис
9) заполнить красиво readme 
10) скниуть по мэил мое решение
11) добавить пагинацию (сколько за раз репозиториев в ответе)
12) логирование
13) oauth2 регистрация


request: 
endpoint uri: api/github/{username}/repos

response when found repos by username:
{
"repository_name": "test",
"owner_login": "owner_login",
"branches": [
{
"branch_name": "branch1",
"last_commit_sha" : "last commit sha"
},
{
"branch_name": "branch2",
"last_commit_sha" : "last commit sha"
}
]
}

response when not found repos by username:
receive 404 response in such a format:
{
    “status”: ${responseCode}
    “message”: ${whyHasItHappened}
}
