# 中文废话生成器
一个用于生成无意义废话的工具

* ## 词库
此项目的词语储存在 `resources/word/word.json` 内。要扩展词库，只需往相应的词类中加入词语。
* `"noun"`：名词
* `"vrbt"`：及物动词
* `"vrbi"`：不及物动词
* `"said"`：说，用于引用句（例：说过）
* `"thnk"`：想，用于思考句（例：思考）
* `"adjt"`：形容词（例：美丽）
* `"advb"`：副词（例：逐渐）
* `"bfvb"`：副词，但不带“地”（例：已经）
* `"degr"`：程度副词（例：十分）
* `"name"`：名字（例：鲁迅）
* `"crea"`：因连接词（例：因为）
* `"cres"`：果连接词（例：所以）
* `"prep"`：方位（例：里面）  

你可以自行添加词类，键名必须是四个字符。  
* ## 句子结构
句子结构储存在 `resources/sentence_structure/sentence_structure.json` 内。  
以下是该 json 文件的结构：
```json
{
  "exmp": { //父句子种类，必须为四个字符
    "example": { //子句子种类，任意长度。父种类内可以包含多个子种类
      "locked": false, //是否不能被随机抽取，不添加默认为 false（布尔值）
      "weight": 1.0, //权重（双精度浮点型）
      "sentences": [
        "句子模板1", "句子模板2", "句子模板n……" //任意个句子模板
      ]
    }
  }
}
```
### 句子模板的抽取
程序每次生成句子时，会从文件中抽取一个**父种类**，再根据每个子种类的权重（ `"weight"` ）
抽取其中一个**子种类**，最后从 `"sentences"` 数组中抽取一个句子模板。  
如该**子种类**被锁定（ `"locked"` 为 true ，不添加默认为 false ），则不会抽取该子种类（用于句子嵌套，后面会介绍）。
### 句子模板的结构
#### 基本模板结构、插入词语
句子模板是由字符、标点、词语代号、组成的字符串，用于生成句子。  
在句子模板加入 / + 词语代号 可以从 `word.json` 随机抽取一个该代号的词语。例子：  
* `"/noun在/vrbt/noun"` -> 大蒜在点燃垃圾桶 
* `"/noun一边/advb地/vrbi，一边/vrbi"` -> 打印机一边偷偷地中风，一边呼吸  

#### 句子嵌套
在句子模板中加入 ~ + 父种类代号 可以嵌套该父种类内随机另一个句子模板，**无视子种类是否被锁定**。  
例子：
```json
//sentence_structure.json内
{
  "tsta": {
    "test_sub": {
      //不加 "locked" ，默认为false。 
      "weight": 1.0,
      "sentences": [
        "/noun是/adjt的"
      ]
    }
  },
  "tstb": {
    "test_sub": {
      "locked": true,
      "weight": 1.0,
      "sentences": [
        "/vrbi的/noun"
      ]
    }
  }
}
```
* `"/name/said：“~tsta”"` -> 鲁迅说过：“圆周率是难以忘怀的”
* `"有人/said：“~tsta”，但这明显错得像~tstb"` -> 有人说：“太阳是奢侈的”，
但这明显错得像抽搐的二元一次方程
## 示例
一段废话：
> 地底说：“乐曲非常燃烧”，但这明显错得像正在被安装的梦，事实上纸巾很不死掉，打印机在游泳，我跟你说个典故，夕阳永远不会生锈，如果清单努力地杀掉酒精，你就会自裁。我跟你说个典故，彗星是弱小的，如果下水道难以置信悲伤，乌鸦就会游泳。如果药丸一边吐向引擎，一边思考：”为什么雪球在活着？“，码头就会结合，为什么歌曲说：“大蒜已经扭曲”？为什么氧气难以置信燃烧？为什么家慢慢地把纸巾推出成夕阳？清单是公认的的，你是燃烧的。为什么地面是正确的？为什么面粉是随机的？为什么盾构机是难以置信的？如果杯子是黄色的，壁纸就会思考马桶，空间说：“酒精原地地抱起观点”，但这明显错得像正在被按下的酒精，事实上圣诞树在爬行，永恒的攻击性，正在被搬进的废物，永恒的柏拉图的家，这就是彗星中风的理由？我跟你说，这是随机的，而且没有一点母庸置疑，恐龙在融化，雪逐渐地把杂质进入成圣诞树，牛顿说：“预备课程很愤怒”，