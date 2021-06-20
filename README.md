# Приложение «Мобильный хоспис»
## Правила нейминга в проекте (*для разрабочиков*):
* Для логики, фрагментов, функций и т.п.:
*Используется Cammel Case*

  Примеры:
  1. class MainFragment : Fragment() {}
  2. fun convertDate(): String {}
* Для нейминга в xml используется Snake Case. Завершаищим элементом в названии должен идти тип view (полностью):
  
  Примеры:
  1. ```html 
          <ImageView
            android:id="@+id/line_divider_image_view"
            ...
            android:contentDescription="@string/line_divider_description" />
```
  2. ```html 
  <string name="date_not_set">Date not set</string>
  ```
