# JAVA�ƈႤ���@�ŁA�n�}�������e�𐮗�����B

~~~javascript
var bookmarks;
bookmarks = { key1: 'value1' }; // Object1���w��
bookmarks = { key2: 'value2' }; // Object2�w����AObject1���㏑������B
bookmarks = { key3: 'value3' }; // Object3�w����AObject2���㏑������B

console.log('<-- bookmarks test start -->');
console.log(bookmarks['key3']); // Object3�̃L�[�w����A�l���擾����B
 
bookmarks['key3'] = 'value4';�@ // �� Object3�̃L�[���w�肵�āA�V�����l���w�肷��B
console.log(bookmarks['key3']); // �V�����l���m�F����B

console.log(Object.keys(bookmarks));�@// Object3�̃L�[���o�͂���B

console.log('<-- bookmarks test end -->');
~~~