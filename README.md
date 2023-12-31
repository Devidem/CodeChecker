# Цель автотеста
Целью автотеста является проверка правильности отображения скидок на страницах товаров сайта Citilink. 

Проект предназначен для демонстрации навыков приобретенных в процессе изучения автотестирования.

# Изначальные требования к автотесту
Тест должен иметь возможность параметризации, а именно возможность выбора разных источников данных для проверки (.xls и PostgreSQL) и браузеров (Chrome и Firefox), с помощью которых будет проводится проверка, а также выбор способа выполнения - однопоточный или многопоточный, с возможностью установки количества потоков.

Результатом проверки должен стать отчет содержащий результаты для каждой промо-акции товара.

Приложение должно отвечать принципам ООП и SOLID.

# Ознакомительное Youtube видео

В начале видео делается обзор содержимого, затем краткое описание и демонстрация работы приложения, а после описываются все основные решения проекта в порядке убывания интересности. 

Я пострался сделать его максимально удобным для просмотра - анимации, таймкоды и т.д..

Также весь код сопровожден подробными обычными и документационными комментариями.

**Нажмите на картинку для просмотра видео!**
<a href="https://youtu.be/9eCInOM61yE" target="_blank"><img src="https://lh3.googleusercontent.com/u/0/drive-viewer/AITFw-xrGfVQT8qt3KhSq1nRZ4CfTRwOMX1xTMoGmK7ApBgo9rtlAxyO-jDdhJgoQEuIMzdqp71GT1D7N1q2dTtTlusLbFhlTw=w1920-h969" 
alt="Ознакомительное Youtube видео" width="1287" height="500" border="10" /></a>

**Таймкоды из описания видео (для понимания содержимого):**

00:00 | Дисклеймер

00:21 | Краткое содержание
***
00:40 | 1 - Цели автотеста
***
01:11 | 2 - Используемые  инструменты
***
01:36 | 3 - Демонстрация
***
03:11 | 4 - Подробное описание основных решений

03:22 | ООП и навигация на страницах

05:19 | Буфер Вебдрайверов

06:11 | ApiUI буфер

06:46 | Игнорирование TimeOutException для ускорения тестирования

07:21 | Динамический implicitwait

07:49 | Использование RestAssured(API) в UI тестах

08:10 | Интерфейсы Screenshootable и Retryable для Listener”ов и RetryAnalyzer”ов

09:19 | Параметризация DataProvider и вспомогательные классы Initializator и BufferSuiteVar

10:31 | Использование “Фантиков” для отчета Allure

10:57 | Группировка тестов по категориям и другие настройки отчета Allure

11:35 | Обрезание скриншотов для отчета Allure

11:46 | Собственные исключения

12:29 | Многопоточность в реализации Selenium + Java only

12:49 | Динамические и статические локаторы в Enum

13:33 | Философия использования Enum

14:10 | Параметризация запуска нужного Suite в Maven

14:27 | Реализация проверки промо-акций в API

15:14 | Реализация проверки промо-акций в UI

16:14 | Динамический DataProvider для UI теста

16:27 | Защита Listener и RetryAnalyzer от некорректного запуска

16:42 | Расширение ObjectEx для обработки Excel и SQL

17:08 | Получение Xpath локатора по Property элемента
***
17:37 | SOLID принципы и их применение

19:24 | Настройщик вебдрайверов 

19:33 | Селекторы Browsers, InputType, Files

19:44 | Класс для обработки Excel файлов

20:23 | Класс для обработки массивов

20:43 | Класс для работы с базой SQL

21:10 | Класс для хранения Api запросов

21:14 | Файловый менеджер

21:19 | Suite Reader
***
21:23 | Финал

# Реализации и запуск автотеста

Всего сделано две реализации:

1. Java + Selenium

   Запуск и параметризация делаются из файла [Runner.java](https://github.com/Devidem/CodeChecker/blob/master/src/main/java/tests/citilink/javaSelenOnly/Runner.java), который находится в папке [javaSelenOnly](https://github.com/Devidem/CodeChecker/tree/master/src/main/java/tests/citilink/javaSelenOnly). Итоги проверки сохраняются в .xls файл в папку [Excel](https://github.com/Devidem/CodeChecker/tree/master/Outputs/Excel) - в названии файла указываются результат и дата проведения проверки.
  
2. Java + Selenium + RestAssured + TestNG + Allure
  
   Запуск и параметризация делаются из файла [final.xml](https://github.com/Devidem/CodeChecker/blob/master/src/main/java/tests/citilink/finalTest/final.xml), который находится в папке [finalTest](https://github.com/Devidem/CodeChecker/blob/master/src/main/java/tests/citilink/finalTest). Результаты проверки сохраняются в папку [allure-results](), откуда с помощью Allure Plugin можно сформировать итоговый отчет.

# Вебдрайверы

Вебдрайверы хранятся в папке [SelenDrivers](https://github.com/Devidem/CodeChecker/tree/master/SelenDrivers) и забираются оттуда автотестом.

*Рекомендую заменить на собственные, в соответствии с версиями установленных у вас браузеров.

# Входные данные

Входные данные для теста формируются из двух таблиц: 

- Первая - содержит список кодов товаров с именами всех существующих промоакций и значениями их доступности для этого товара.
  
- Вторая - содержит имена промоакций и их текущий статус активности.
  
В качестве источников входных данных для тестирования используются .xls файлы и PostgreSQL:

- .xls файл хранится в папке [Files](https://github.com/Devidem/CodeChecker/blob/master/Inputs/Files). Две ранее упомянутые таблицы расположеный на первом и втором листе файла. В папке должен находится только один .xls файл для корректного запуска - в противном случае получите ошибку.

- В папке [SQL](https://github.com/Devidem/CodeChecker/blob/master/Inputs/SQL) находится дамп базы данных PostgreSQL. Перенастройку параметров подключения БД можно сделать через Enum [PostgreData](https://github.com/Devidem/CodeChecker/blob/master/src/main/java/enums/PostgreData.java), который находится в папке [enums](https://github.com/Devidem/CodeChecker/blob/master/src/main/java/enums).

**ВАЖНО!** 

Имена промо-акций для тестирования забираются из названий столбцов таблиц, поэтому они должны быть корректными.
