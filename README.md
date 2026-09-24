# casa-em-dia

API REST para gerenciar tarefas da casa (diárias, semanais e mensais).

## Rodar
```bash
mvn spring-boot:run
```
Console H2: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:file:./data/casaemdia, user sa, senha vazia)

## Endpoints
- GET /tarefas — lista tarefas ativas (com feitaHoje)
- GET /tarefas/{id} — busca uma tarefa
- POST /tarefas — cria tarefa
- PUT /tarefas/{id} — atualiza tarefa
- DELETE /tarefas/{id} — remove tarefa e histórico
- POST /tarefas/{id}/concluir — marca como feita hoje

Exemplo de corpo (POST /tarefas):
```json
{
  "nome": "Lavar louça",
  "comodo": "Cozinha",
  "frequencia": "DIARIA",
  "periodoDoDia": "NOITE",
  "horario": "21:00"
}
```
