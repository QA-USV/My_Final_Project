# **ТЕСТ ПЛАН**
# **Тестирование функционала мобильного приложения "Мобильный хоспис"**

## **1. Введение**
Настоящий Тест план разработан для информирования заинтересованных лиц* о подходе к ручному и автоматизированному тестированию функционала приложения, указанного ниже.
Тест план включает в себя описание объекта тестирования, цели и объема тестирования, виды тестирования, ресурсы и время, требуемые для тестирования, а также определяет ожидаемые результаты тестирования и риски, связанные с настоящим планом (далее – Проект).

> "Заинтересованными лицами" в целях настоящего плана признаются любые лица, обладающие правом доступа к настоящему документу, включая, но не ограничиваясь, разработчиками и тестировщиками.
---

1.1. Объект тестирования: мобильное приложение "Мобильный хоспис" (далее также - Приложение).

1.2. Функционал Приложения:

    - размещение информации о заявках и функционал для работы с ними;
    - новостная сводка хосписа;
    - тематические цитаты.

1.3. Версии Android, совместимые с Приложением: Android 6.0 Marshmallow - Android 13 Tiramisu.

1.4. Требования Заказчика к приложению: нет данных.

1.5. Предположения и допущения (ввиду отсутствия требований к Приложению):

    1.5.1. целевой регион: РФ;
    1.5.2. целевая аудитория: работники хосписов;
    1.5.3. язык целевой аудитории: русский.
    1.5.4. роли пользователей:
            - администратор;
            - работник хосписа.
    1.5.5. права пользователей:
            - администратор:
                * полный доступ со всеми правами;
            - работник хосписа:
                * чтение, сортировка и фильтрация новостей, заявок, цитат;
                * создание заявки;
                * оставление комментариев к заявке;
                * изменение своего комментария к заявке;
                * удаление своей заявки;
                * принятие заявки, созданной другим работником, к своему исполнению;
                * отказ от принятой им к исполнению заявки;
                * отметка об завершении исполнения принятой им заявки;

## **2. Стратегия тестирования**
Объем настоящего Проекта ограничен исключительно тестированием реализованных функций Приложения, описанных выше.

### **2.1. Типы тестирования**

2.1.1. Функциональное тестирование:

       i) Проверка всех функций приложения на соответствие требованиям (при наличии).
       ii) Проверка логики приложения и корректности взаимодействия между компонентами.

2.1.2. Тестирование локализации.

2.1.3. Тестирование UI (пользовательского интерфейса):

       i) Проверка внешнего вида и оформления приложения.
       ii) Проверка внешнего вида и оформления приложения в Dark Mode.
       iii) Проверка внешнего вида и оформления приложения в режиме экрана Landscape.
       iv) Проверка удобства использования интерфейса и элементов управления.

2.1.4. Совместимость:

       i) Тестирование приложения на разных смартфонах и версиях Android.
       ii) Тестирование приложения на разных планшетах и версиях Android.

### **2.2. Сценарии тестирования:**

**2.2.1. Тестирование авторизации**

2.2.1.1. Авторизация с валидными логином и паролем.

         Ожидаемые результат: успешная авторизация, открытие главной страницы;

2.2.1.2. Авторизация с невалидными логином/паролем;

         Ожидаемый результат: отказ в авторизации, открытие окна сообщения об ошибке;

2.2.1.3. Авторизация с пустым логином/паролем;

         Ожидаемый результат: отказ в авторизации, открытие окна сообщения об ошибке.

**2.2.2. Тестирование локализации**

2.2.2.1. Проверка языка интерфейса.

         Ожидаемый результат: язык интерфейса русский;

2.2.2.2. Проверка формата дат и времени.

         Ожидаемый результат: формат дат и времени европейский.

**2.2.3. Тестирование функционала главной страницы**

2.2.3.1. Открытие страницы Новости через разные маршруты навигации.

         Ожидаемый результат: открытие страницы Новости;

2.2.3.2. Открытие страницы Заявки через разные маршруты навигации.

         Ожидаемый результат: открытие страницы Заявки;

2.2.3.3. Открытие страницы О приложении.

         Ожидаемый результат: открытие страницы О приложении;

2.2.3.4. Открытие страницы Любовь – это всё.

         Ожидаемый результат: открытие страницы Любовь – это всё;

2.2.3.5. Возврат на главную страницу со страниц Новости, Заявки, О приложении, Любовь – это всё разными маршрутами навигации и функциональных кнопок устройства.

         Ожидаемый результат: открытие главной страницы;

2.2.3.6. Создание заявки с главной страницы.

         Ожидаемый результат: заявка создана.

**2.2.4. Тестирование функционала страницы Новости**

2.2.4.1. Сортировка новостей по дате создания.

         Ожидаемый результат: сформирован список новостей по дате создания;

2.2.4.2. Фильтрация новостей по Категориям, периоду создания, статусу.

         Ожидаемый результат: сформирован отфильтрованный список новостей;

2.2.4.3. Раскрытие и свертывание описания новостей.

         Ожидаемый результат: описание новостей разворачивается и сворачивается;

2.2.4.4. Переход на страницу Контрольная панель.

         Ожидаемый результат: открытие страницы Контрольная панель.

**2.2.5. Тестирование функционала страницы Контрольная панель**

2.2.5.1. Сортировка новостей по дате создания.

         Ожидаемый результат: сформирован список новостей по дате создания;

2.2.5.2. Фильтрация новостей по Категориям, периоду создания, статусу.

         Ожидаемый результат: сформирован отфильтрованный список новостей;

2.2.5.3. Раскрытие и свертывание описания новостей.

         Ожидаемый результат: описание новостей разворачивается и сворачивается;

2.2.5.4. Переход к странице Создание новостей.

         Ожидаемый результат: открытие страницы Создание новостей;

2.2.5.5. Переход к странице Редактирование новостей.

         Ожидаемый результат: открытие страницы Редактирование новостей;

2.2.5.6. Удаление новости.

         Ожидаемый результат: новость удалена.

**2.2.6. Тестирование функционала страницы Создание новостей**

2.2.6.1. Создание новости с валидными данными.

         Ожидаемый результат: новость создана;

2.2.6.2. Создание новости с невалидными данными, пустыми полями, пробелами в полях.

         Ожидаемый результат: новость не создана, открытие окна сообщения об ошибке;

2.2.6.3. Проверка верхних и нижних граничных значений полей.

         Ожидаемый результат: при несоответствии значений - открытие окна сообщения об ошибке.


**2.2.7. Тестирование функционала страницы Редактирование новостей**

2.2.7.1. Редактирование новости валидными данными.

         Ожидаемый результат: новость изменена;

2.2.7.2. Редактирование новости невалидными данными, с пустыми полями, пробелами в полях.

         Ожидаемый результат: новость не изменена, открытие окна сообщения об ошибке;

2.2.7.3. Проверка верхних и нижних граничных значений полей.

         Ожидаемый результат: при несоответствии значений - открытие окна сообщения об ошибке.

**2.2.8. Тестирование функционала страницы Заявки**

2.2.8.1. Сортировка заявок по дате создания. (функционал не реализован).

         Ожидаемый результат: сформирован список заявок по дате создания;

2.2.8.2. Фильтрация заявок по периоду создания, статусу. (функционал фильтрации по периоду создания заявки не реализован).

         Ожидаемый результат: сформирован отфильтрованный список заявок;

2.2.8.3. Раскрытие и свертывание карточки заявки.

         Ожидаемый результат: карточка заявки разворачивается и сворачивается;

2.2.8.4. Оставление комментариев в карточке заявки.

         Ожидаемый результат: комментарий отображается в карточке заявки;

2.2.8.5. Изменение комментариев в карточке заявки.

         Ожидаемый результат: комментарий в карточке заявки изменен;

2.2.8.6. Удаление комментариев в карточке заявки. (функционал не реализован).

         Ожидаемый результат: комментарий в карточке заявки удален;

2.2.8.7. Принятие заявки к исполнению.

         Ожидаемый результат: заявки принята к исполнению исполнителем;

2.2.8.8. Отмена заявки со статусом «Открыта».

         Ожидаемый результат: заявка отменена;

2.2.8.9. Отказ исполнителя от исполнения заявки.

         Ожидаемый результат: заявка вновь открыта для исполнения;

2.2.8.10. Отметка о завершении исполнения заявки.

         Ожидаемый результат: заявкав отмечена исполненной;

2.2.8.11. Проверка верхних и нижних граничных значений полей.

         Ожидаемый результат: при несоответствии значений - открытие окна сообщения об ошибке;

2.2.8.12. Переход к странице Создание заявки.

         Ожидаемый результат: открытие страницы Создание заявки;

2.2.8.13. Переход к странице Редактирование заявки.

         Ожидаемый результат: открытие страницы Редактирование заявки.

**2.2.9. Тестирование функционала страницы Создание заявки**

2.2.9.1. Создание заявки с валидными данными.

         Ожидаемый результат: заявка создана;

2.2.9.2. Создание заявки с невалидными данными, пустыми полями, пробелами в полях.

         Ожидаемый результат: заявка не создана, открытие окна сообщения об ошибке;

2.2.9.3. Проверка верхних и нижних граничных значений полей.

Ожидаемый результат: при несоответствии значений - открытие окна сообщения об ошибке.

**2.2.10. Тестирование функционала страницы Редактирование заявок**

2.2.10.1. Редактирование заявки валидными данными.

          Ожидаемый результат: заявка изменена;

2.2.10.2. Редактирование заявки невалидными данными, с пустыми полями, пробелами в полях.

          Ожидаемый результат: заявка не изменена, открытие окна сообщения об ошибке;

2.2.10.3. Проверка верхних и нижних граничных значений полей.

          Ожидаемый результат: при несоответствии значений - открытие окна сообщения об ошибке.

**2.2.11. Тестирование функционала страницы О приложении**

2.2.11.1. Доступ по ссылке к тексту Политики конфиденциальности.

          Ожидаемый результат: Текст Политики конфиденциальности доступен для ознакомления;

2.2.11.2. Доступ по ссылке к тексту Условия использования Приложения.

          Ожидаемый результат: Текст Условия использования Приложения доступен для ознакомления.

**2.2.12. Тестирование функционала страницы Любовь – это всё**

2.2.12.1. Раскрытие и свертывание цитат.

          Ожидаемый результат: цитаты разворачивается и сворачивается;

**2.2.13. Тестирование страниц в режиме Dark Mode**

2.2.13.1. Видимость, корректность отображения приложения в режиме Dark Mode.

          Ожидаемый результат: Весь текст, все иконки и кнопки страниц хорошо видимы и корректно отображаются.

**2.2.14. Тестирование страниц в режимах Landscape и Portrait, с разным разрешением экрана**

2.2.14.1. Видимость, корректность отображения приложения в режимах Landscape и Portrait.

          Ожидаемый результат: Весь текст страниц, все иконки и кнопки приложения корректно отображаются.

**2.2.15. Тестирование совместимости**

2.2.15.1. Работа приложения на устройствах с Android 6.0. Marshmallow и Android 13 Tiramisu.

          Ожидаемый результат: приложение работает в соответствии с требованиями.

### **2.3. Исключения**

В рамки настоящего Проекта не входят любые иные виды нефункционального и функционального тестирования, кроме прямо поименованных в разделе 2.2. настоящего плана.
Тестирование функционала web-приложения (при его наличии) не входит в рамки Проекта.

## **3. Критерии тестирования **

### **3.1 Приостановка тестирования**

Критерии приостановки: при падении более 40 % тестов тестирование подлежит приостановке до устранения командой разработчиков причин такого падения.

### **3.2 Завершение тестирования**

Критерии успешного завершения этапа тестирования: запуск и прохождение 100% тестов.

## **4. Ресурсы **

### **4.1. Системные ресурсы**

| No. | Ресурсы   | Описание                                                                                |
|-----|-----------|-----------------------------------------------------------------------------------------|
| 1   | Сервер    | Сервер с установленной SUT, базой данных                                                |
| 2   | Компьютер | Windows 10, RAM min 2GB, Chrome Ver XXX.XX (64-bit), монитор мин. 15', клавиатура, мышь |
| 3   | Network   | LAN или Wi-Fi internet line, скорость не менее 0,5 Mb/s                                 |
| 4   | Test tool | Android Studio, Espresso, DevTools, Allure                                              | 
| 5   | Test tool | Physical device HONOR 20, model YAL-L21, Build number: 12.0.0.221,                      |
|     |           | OS: Magic UI 5.2.0., RAM 6.0 Gb                                                         | 
| 6   | Test tool | Physical device HUAWEI Mate 20 pro, model YAL-L29, Build number: 12.0.0.142,            |
|     |           | OS: EMUI 12.0.0., RAM 6.0 Gb                                                            | 
| 7   | Test tool | Physical device SAMSUNG Galaxy, model SM-J510FN,                                        |
|     |           | OS: Android 7.1.1., Build number: NMF26X.J510FNXXS3BTT11, RAM 2.0 Gb                    | 


### **4.2. Человеческие ресурсы**

| No. | Участник проекта | Задачи                                                                                                                                                                                                                           |
|-----|------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1   | QA-Инженер       | Управляет проектом, определяет направления проекта, обеспечивает требуемые ресурсы, определяет и описывает методы ручного и автоматизированного тестирования, исполняет тесты, ведет учет результатов, готовит отчеты о дефектах |


## **5. Разрешения и допуски**

| No. | Разрешения и допуски                                | Описание                     |
|-----|-----------------------------------------------------|------------------------------|
| 1   | Корректный логин и пароль для роли Администратор    | Для авторизации в приложении |
| 2   | Корректный логин и пароль для роли Работник хосписа | Для авторизации в приложении |

## **6. Расписание и оценка**

| Задача                                         | Сотрудник   | Оценка чел./ч |
|------------------------------------------------|-------------|---------------|
| Подготовка чек листа                           | QA-Инженер  | 8 чел./ч      |
| Подготовка тест-кейсов                         | QA-Инженер  | 24 чел./ч     |
| Написание автоматизированных тестов            | QA-Инженер  | 40 чел./ч     |
| Исполнение автоматизированных тестов           | QA-Инженер  | 5 чел./ч      |
| Исполнение ручных тестов                       | QA-Инженер  | 3 чел./ч      |
| Подготовка отчета о тестировании, баг-репортов | QA-Инженер  | 16 чел./ч      |
| **Итого**                                      |             | **96 чел./ч** |

## **7. Тестовая документация**

7.1 Перед тестированием
- Тест план
- Чек лист
- Тест-кейсы

7.2 После тестирования
- Отчет о тестировании
- Баг-репорт