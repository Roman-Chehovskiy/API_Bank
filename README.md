# API_Bank
Опиание проекта:
Позволяет просмотреть баланс пользователя, увеличить или уменьшить баланс на заданную сумму по id пользователя
Возвращает ответ в формате Json

localhost:8080
Точка входа в приложение: /api/bank

Функции:
1)Получить баланс пользователя по его Id
  /api/bank/getBalance/id={id}, где {id}(integer) - это id пользователя
2)Увеличить баланс
  /api/bank/putMoney/id={id}&&money={money}, где {id} - id пользователя, {money}(double) - сумма, на которую увеличиваем баланс
3)Уменьшить баланс
  /api/bank/takeMoney/id={id}&&money={money}, где {id} - id пользователя, {money} - сумма, на которую уменьшаем баланс
4)Получить список операций по id пользователя, в заданный промежуток времени
  /api/bank/getOperation/id={id}&&startDate={startDateStr}&&endDate={endDateStr}, где {id} - id пользователя, {startDate} - начало периода, за который необходимо получить операции, {endDate} - окончание периода. Если начало или конец периода не указаны, то передается '0'.
  Формат даты '2023-12-24'
  Напирмер /api/bank/getOperation/id=12&&startDate=0&&endDate=2023-01-01 выдаст список всех операций выполненных пользователем с id=12, за все время до 01 января 2023 года
5)Перевод средств от одного пользователя другому
  /api/bank/transferMoney/senderId={senderId}&&recipientId={recipientId}&&money={money}, где {senderId} - id отправителя, {recipientId} - id получателя, {money} - сумма перевода
  
  er-diarama и dump файл БД расположенны resourses/dataBase 
