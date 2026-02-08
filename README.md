## Started Information

##### Запуск плагина:
 1. Скачайте последнюю версию с realeses или curseforge
 2. Установите на сервер в папку mods
 3. Запустите сервер

##### Команды плагина:

 - /placeholders check (argument) - Проверка любого заменителя в режиме реального времени
 - /placeholders reload - Перезагрузка плагина

##### Права плагина:

 - fplaceholders.check - Право на использование команды check
 - fplaceholders.reload - Право на использование команды reload

## Javascript

Мод предоставляет функцию использования Javascript-кода для заменителей. Все скрипты находятся в корневой папке плагина в папке javascript. Скрипты не проходят регистрацию в конфигурации основного плагина, она происходит автоматически.

Файл test.js

    function checked() {
    	return "Hello, Guy";
    }
    
    checked();

Использование скрипта - %javascript_test%

**Еще несколько примеров, показывающих разный вид работы:**

    var placeholder = papi("%player_prefix%");
    
    function checked() {
    	return placeholder;
    }
    
    checked();
###
    var placeholder = player.getUsername();
    
    function checked() {
    	return placeholder;
    }
    
    checked();
**!** Объект (player) предоставляется в каждый заменитель вида .js, который вызван для игрока. Если вызван без игрока - на выходе null

## Extansions

Есть возможность создавать свои плагины, используя API для заменителей. Также предоставляется возможность создавать мини-скрипты (.jar), которые помещаются напрямую в папку fPlaceholders/plugins

Maven:

```markup
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
```markup
	<dependency>
	    <groupId>com.github.FLOERKA</groupId>
	    <artifactId>fPlaceholders</artifactId>
	    <version>Version</version>
	</dependency>
```
Gradle:
```css
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
```
```css
	dependencies {
	        implementation 'com.github.FLOERKA:fPlaceholders:Tag'
	}
```
##
Пример использования API для создания заменителей:
```java
package ru.floerka.placeholdertest;  
  
import com.hypixel.hytale.server.core.Message;  
import com.hypixel.hytale.server.core.universe.PlayerRef;  
import ru.floerka.placeholders.api.CustomPlaceholder;  
import ru.floerka.placeholders.manager.models.ExecutePlaceholder;  
  
public class CustomExpansion extends CustomPlaceholder {  
    @Override  
  public String getAuthor() {  
        return "floerka";  
    }  
  
    @Override  
  public String getPrefix() {  
        return "test";  
    }  
  
    @Override  
  public String onServerRequest(ExecutePlaceholder placeholder) {  
        String arg = placeholder.getArgs()[0];  
        return "Было введено: " + arg;  
    }  
  
    @Override  
  public String onPlayerRequest(PlayerRef playerRef, ExecutePlaceholder placeholder) {  
        String arg = placeholder.getArgs()[0];  
        playerRef.sendMessage(Message.raw("Ты использовал заменитель!"));  
        return "Было введено: " + arg;  
    }  
}
```
