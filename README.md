springboot-multiple-datasource
==============================

これは何
--------

Spring Boot + Spring Data JPA + Hibernateを使って複数のRDBに接続し、それぞれのDBに対してJPAで操作を行ったサンプル。

実現できたこと
--------------
### 複数のRDBへの接続
特に、それぞれ異なるJDBCドライバを使うことができるため、異なったノードにある異なるRDBMSに接続できる。必要なのはJDBCドライバだけ。
### RDBへの接続設定を簡単に
SpringBootでRDBを設定するのと同じ設定で動かせるようにした。
実際にSpringBootのAutoConfigureの設定クラスの仕組みを流用したので、標準の設定と同じくプロパティファイルを書くだけでHikariCPを使ったコネクションプールを起動し、接続するようにできた。(サンプルではコネクションプールはHikariCP固定)
### JPAプロバイダを複数起動
HibernateをRDBごとに起動しているので、どのDBに対してもJPA/Hibernateを利用するメリットを得られる。
### Hibernateの設定も簡単に
RDBへの接続と同じくSpringBoot標準と同じように設定可能。

実現できなかったこと
-------------------
### 分散トランザクション管理
トランザクションはRDBごとに個別に管理するので、後からコミットしたトランザクションがコミットに失敗すると先にコミットしたトランザクションはロールバックできない。
