# Приложение «Мобильный хоспис»
## **ПРОЦЕДУРА ЗАПУСКА АВТО ТЕСТОВ**

## **1. Окружение**
1.1. Персональный компьютер с установленной Android Studio;

1.2. Физические устройства: мобильный телефон и планшет на базе Android версии не ниже 7.1.1.;

1.3. Виртуальные устройства: эмуляторы мобильного телефона и планшета на базе Android API 23 - API 29;

1.4. Network: LAN или Wi-Fi internet line, скорость не менее 0,5 Mb/s.


## **2. Подготовка физических и виртуальных устройств**
2.1. Переключить системный язык на русский;

2.2. В режиме "Для разработчиков" отключить анимацию (раздел Рисование);

2.3. Включить режим отладки по USB (для физических устройств);

2.4. Отключить Темную тему.

## **3. Переменные и методы**
3.1. Переменные и методы для тестов помещены в папку ValuesForTests;

3.2. Будьте внимательны при изменении переменных и методов. Произвольное и необдуманное их изменение может повлечь неустойчивость тестов.

3.3. Как правило, возможно изменение переменных, устанавливающих значения/содержание:

     * часов и минут
        Например, 
            public int planHour = 10;
            public int planMinutes = 30;
            public int invalidPlanHour = 24;
            public int invalidPlanMinutes = 60;
    * дней (как правило, до и после текущей даты) 
        Например,
            public int daysToExecuteClaim = 3;
            public int daysToPublishNews = 2;
    * диапазона для поиска случайной позиции в иерархии в RecycleView
            public int min = 10;
            public int max = 70;
            public int positionNum = new Random().nextInt(max - min + 1) + min;
    * Toast, при их отсутствии в текущей версии Приложения (нереализованный функционал)
        Например,
            public String invalidSymbolsNotification = "Поле может содержать только русские буквы и цифры";
            public String symbolsOutOfEdgesTitleNotification = "Поле Заголовок или Тема должно содержать от 5 до 50 символов";
    * текста, вводимого в тестируемые тестовые поля
        Например,
            public String moreThen50ValidSymbolsTitleOrComment = "Этот пример ввода текста содержит более 50 валидных символов.";
    * текста, являющегося ожидаемым результатом
        Например,
            public String cutSymbolsMoreThen50InTitleOrComment = "Этот пример ввода текста содержит более 50 валидны";
3.4. Не подлежат изменению методы, а также переменные, определенные в Приложении

      Например,
          public String takeClaimForExecutionButton = getApplicationContext().getString(R.string.take_to_work);

## **4. Запуск тестов**
Выберите в папке AdminRoleTests интересующую Вас группу тестов.

Запустите (Run) тесты.
