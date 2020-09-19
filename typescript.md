# JAVAと違う文法で、ハマった内容を整理する。

~~~javascript
var bookmarks;
bookmarks = { key1: 'value1' }; // Object1を指定
bookmarks = { key2: 'value2' }; // Object2指定より、Object1を上書きする。
bookmarks = { key3: 'value3' }; // Object3指定より、Object2を上書きする。

console.log('<-- bookmarks test start -->');
console.log(bookmarks['key3']); // Object3のキー指定より、値を取得する。
 
bookmarks['key3'] = 'value4';　 // ※ Object3のキーを指定して、新しい値を指定する。
console.log(bookmarks['key3']); // 新しい値を確認する。

console.log(Object.keys(bookmarks));　// Object3のキーを出力する。

console.log('<-- bookmarks test end -->');
~~~

  public Optional<Member> selectByEmail(String email) {
    List<Member> member = em.createQuery("select m from Member m where m.email = :email", Member.class)
        .setParameter("email", email)
        .getResultList();
    return member.isEmpty() ? Optional.empty() : Optional.of(member.get(0));
  }
