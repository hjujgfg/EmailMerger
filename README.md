###### Email merger

Программа объединяет пользователей в одного на основе совпадающих имейлов.

Пример входных данных:

`user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru`<br>
`user2 -> foo@gmail.com, ups@pisem.net`<br>
`user3 -> xyz@pisem.net, vasya@pupkin.com`<br>
`user4 -> ups@pisem.net, aaa@bbb.ru`<br>
`user5 -> xyz@pisem.net` <br>

которые приводятся к: 
`user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru, ups@pisem.net, aaa@bbb.ru` <br>
`user3 -> xyz@pisem.net, vasya@pupkin.com`<br>

 Порядок вывода не гарантирован, зато гарантируется уникальность имейлов в рамках одного пользователя.
 В случае, когда на вход подано два юзера с одинаковыми имейлами и итоговые множества их имейлов не пересекаются,
  то в выводе будут присутствовать две строки с одинаковыми юзерами, но у каждого будет свой соответствующий набор имейлов.
  
Программа не приемлет некорректных данных, таких как строка без юзера или строка с хотя бы одним незаполненным имейлом.

###### Требования
- Java >= 8
- gradle

###### Запуск
`./gradlew runWithJavaExec --console=plain`
концом ввода считается пустая строка

###### Сложность
В худшем случае алгоритм работает линейно ~ 6 x O(n), так же есть несколько константных операций, дважды в цикле.
В конце работы использует ~ (m + n) * 2 памяти. (Не учитывая расход на лямбды, итераторы и т.п.)
n - количество имейлов
m - количество уникальных юзеров

###### Расширение
Класс `Main` принимает аргументом `InputStream` и `Consumer<String>`, что позволяет заменить стандартный вход/выход на 
что-то другое, пример в тесте `MainFunctionalTest`.
Основные методы возвращают Stream, что потенциально позволит легко, по необходимости, добавить фильтрацию, маппинг и тп. 

