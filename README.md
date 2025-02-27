# Тестовое задание для стажёра Android-направления (зимняя волна 2025)

## Как запустить
- Для запуска приложения необходимо скачать .apk файл из [последнего релиза](https://github.com/Girrafeec/avito-android-trainee-winter-2025/releases/tag/0.2.0).
- Приложение тестировалось на Pixel 5 API 33.
- Проект собирался в Android Studio Ladybug Feature Drop | 2024.2.2 Patch 1

## Что сделано
- Экраны с онлайн и локальной музыкой со списком и всеми требуемыми данными
- Экран воспроизведения со всеми необходимыми данными и контроллерами
- Навигация между экранами с музыкой через BottomBar
- Фоновое воспроизведение музыки

## Что можно улучшить/исправить
С чем проект может стать лучше:
- Вынести всю общую логику в TracksViewModel. Сейчас все еще сохраняется дублирование кода.
- Обрабатывать сетевые ошибки, выводя на экран какой-то баннер поверх основноо экрана.
- Добавить placeholder, если у нас нет треков или они не найдены в результате поиска
- Доп заглушка с кнопкой на экране LibraryTracksScreen для запроса разрешения на доступ к медиа
- Вывести обложку треков, которые хранятся в памяти.
- Добавить трекер сети, и показывать баннер в случае ее отсутствия.
- Добавить стейт загрузки на экран.
- Добавить баннер проигрываемой музыки поверх главного экрана, чтобы к текущему экрану с текущим треком можно было вернуться.
- Брать из API обложки альбома с более высоким качеством для экрана проигрывания трека.
- Добавить в уведомление контроллы.
- Скрывать иконки переключения треков, если "плейлист" кончается.
- Решить вопрос с проигрываем треков при поиске. Как и в какой момент менять текущий список для проигрывания.
