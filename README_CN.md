# CSIA-FileRenamer

![image](https://user-images.githubusercontent.com/36402030/126178106-bfe459cd-1cf7-4880-a2fa-2bc08462c1f1.png)


> (本文使用Google翻譯, 因為看起來很有喜感XDD)

> (但是我校對過了所以沒有問題)

> This readme has a English version. Check it out if you need it! [English Version](https://github.com/t41372/CSIA-FileRenamer/blob/a549887e524531f3f1d8dbc48c7ed90b323838ef/README.md)


本項目完成於2021.03.11, 並且大概率不會再更新, 除非心情好。
<br/>
<br/>
<br/>



這是為我的 IB 計算機科學課程 IA 編寫的文件重命名器（有點像最終項目）。

您可以在其中使用您制定的一組規則輕鬆重命名大量文件。

該程序是用 Java 11 編寫的（最初是 Java 8，但我已將其遷移到 Java 11）。GUI使用了JavaFX。



## 系統要求

- Java 8 或 11。任何高於 11 的版本都可以工作，但我從未測試過……誰知道哈哈



## 下載?

您可以在[release](https://github.com/t41372/CSIA-FileRenamer/releases/tag/v1.0)中找到 .jar 文件（可執行文件）。 



## 如何運行它？

可以通過雙擊運行 Java 11 的那個。

必須使用以下命令運行 Java 8 的那個：

~~~~
java -jar <the jar file>
~~~~



## 如何使用它？

**自己**發現吧！

你是個聰明人，我相信你會憑直覺自動理解我的軟件！

## 它能做什麼？

它可以根據一組規則重命名大量文件。規則如下。

（這些是從我的計劃文檔中復制的，您可以在Documentation/ [Crit_A_Planning.pdf](https://github.com/t41372/CSIA-FileRenamer/blob/a549887e524531f3f1d8dbc48c7ed90b323838ef/Documentation/Crit_A_Planning.pdf) 中找到）

（ps那些規則是很久以前寫的，當時我的英語不好......所以......你知道XD）


規則

- 將文件名中的一些文本“x”替換為一些文本“y”
- 根據數字刪除字符
- 讀取音樂文件的一些標籤信息並將其添加到文件名中。[曲目、專輯、藝術家、標題]
- 在文件名的某處插入一些文本。

其他一些功能

- 將命名規則集導出到文件中，用戶可以在以後加載規則。
- 可以按順序分配和執行多個規則。例如，用戶可以輸入一系列規則來操作文件名，如“用y替換x->刪除前3個字符->插入XXX”
- 用戶可以預覽這些規則，但是*笨蛋*可能無法理解這些規則XD。
- 其他一些功能等你來發現！
- 耶


