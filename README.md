# 中文废话生成器（未完成）
十分无聊的工具，用于生成无意义废话。
## 扩充此项目
* ### 词库
此项目的词语储存在 `resources/word/word.json` 内。  
以下是该 json 文件的结构：  
```json
{
  "exmp（必须为四个字符）": [
    "词语1", "词语2" ,"词语n……"
  ]
}
```
要扩展词库，只需往相应的词类中加入词语。
* `"noun"`：名词（例：电脑）
* `"vrbt"`：及物动词（例：打开）
* `"vrbi"`：不及物动词（例：睡觉）
* `"said"`：说，用于引用句（例：说过）
* `"thnk"`：想，用于思考句（例：思考）
* `"adjt"`：形容词（例：美丽）
* `"advb"`：副词（例：逐渐）
* `"bfvb"`：副词，但不带“地”（例：已经）
* `"degr"`：程度副词（例：十分）
* `"crea"`：因连接词（例：因为）
* `"cres"`：果连接词（例：所以）
* `"prep"`：方位（例：里面） 
* `"mtph"`：比喻词（例：仿佛）
* `"rela"`：某物的关系（例：兄弟）
* `"emot"`：情感（例：愤怒）
* `"excl"`：感叹词（例：天哪）
* `"dcrn"`：修饰过的名词
* `"none"`：空白
* 其他种类为杂项

你可以自行添加词类，键名必须是四个字符。  
* ### 句子结构
句子结构储存在 `resources/sentence_structure/sentence_structure.json` 内。  
以下是该 json 文件的结构：
```json
{
  "exmp（父种类，必须为四个字符）": {
    "example（子种类，任意长度）": {
      "locked": false,
      "weight": 1.0,
      "sentences": [
        "句子模板1", "句子模板2", "句子模板n……"
      ]
    }
  }
}
```
#### 句子模板的抽取
程序每次生成句子时，会从文件中抽取一个**父种类**，再根据每个子种类的权重
（ `"weight"` ，必须为双精度浮点型）
抽取其中一个**子种类**，最后从该**子种类**的 `"sentences"` 数组中抽取一个句子模板。  
如该**子种类**被锁定（ `"locked"` 为 true ，不添加默认为 false ），则不会抽取该**子种类**（用于句子嵌套，后面会介绍）。
#### 句子模板的结构
##### 基本模板结构、插入词语
句子模板是由字符、标点、词语代号、组成的字符串，用于生成句子。  
在句子模板加入 `/词语代号` 可以从 `word.json` 随机抽取一个该代号的词语。例子：  
```json
{
  "main": {
    "mian_sentence1": {
      "weight": 1.0,
      "sentences": [
        "/noun在/vrbt/noun"
      ]
    },
    "mian_sentence2": {
      "weight": 1.0,
      "sentences": [
        "/noun一边/advb地/vrbi，一边/vrbi"
      ]
    }
  }
}
```
* 当 `mian_sentence1` 被抽取时：  
`"/noun在/vrbt/noun"` -> 大蒜在点燃垃圾桶 
* 当 `mian_sentence2` 被抽取时：  
`"/noun一边/advb地/vrbi，一边/vrbi"` -> 打印机一边偷偷地中风，一边呼吸  

#### 句子嵌套
在句子模板中加入 `~父种类代号` 可以嵌套该父种类内随机另一个句子模板，**无视子种类是否被锁定**。  
例子：
```json
{
  "main": {
    "mian_sentence1": {
      "weight": 1.0,
      "sentences": [
        "/name/said：“~tsta”"
      ]
    },
    "mian_sentence2": {
      "weight": 1.0,
      "sentences": [
        "有人/said：“~tsta”，但这明显错得像~tstb"
      ]
    }
  },
  "tsta": {
    "test_sub": {
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
* 当 `mian_sentence1` 被抽取时：  
`"/noun/said：“~tsta”"` -> 鲁迅说过：“圆周率是难以忘怀的”
* 当 `mian_sentence2` 被抽取时：  
`"有人/said：“~tsta”，但这明显错得像~tstb"` -> 有人说：“太阳是奢侈的”，
但这明显错得像抽搐的二元一次方程
### 随机抽取
使用随机抽取格式可以从多个字符串之中抽取其中一个选项。  
随机抽取的格式为 `<选项1,选项2,选项n……>` ，选项数量不限，使用半角逗号分隔。  
选项可以含有词语种类 / 句子种类。  
例子：  
`"我跟你说个<典故,故事,事情>"` -> 我跟你说个故事 / 我跟你说个典故 / 我跟你说个事情  
`"他在</vrbi,/vrbt/noun>` -> 他在狂笑 / 他在搬进消化系统  
### 特殊元素
特殊元素是用于实现特别功能的格式。  
特殊元素由 @# 包围，可以包含词语、句子模板、随机格式。  
* `@RandomInt#`：随机整数
* `@RandomInt=inta#`：0 到 inta 之间的随机整数  
* `@RandomInt=intA=to=intB#`：intA 到 intB 之间的随机整数  
* `@Repeat=strA=for=intA#`：将 strA 重复 intA 遍
* `@Store=strA#`：将 strA 暂存  
* `@Take#`：提取被暂存的字符串  
* `@Store=strA=key=strB`#将 strA 暂存到 strB 键  
* `@Take=strB#`：提取暂存在 strB 键的字符串  
* `@Clear=strA=in=strB#`：清除strB里的strA
* `@EndPunc#`：“！”或“。”其中一个
* `@StipulateWordLength=word=length=intA#`：获取长度为intA的随机词语，word是词语类型

例：  
* `"@RandomInt=10#个/noun"` -> 7个洗衣机盖  
* `"@Repeat=天灾！=for=3"` -> 天灾！天灾！天灾！
* `"@Store=/noun#/vrbt了@Take#"` -> 阿尔卑斯山抬起了阿尔卑斯山  
* `"@Store=/noun=key=A#/vrbt了@Store=/noun=key=B#，而@Take=B#/vrbt了@Take=A#"` -> 时钟点燃了太阳系，而太阳系拿走了时钟
* `@Clear=在=in=你在干什么在#` -> 你干什么
## 示例
时不时更新，最后更新：25/9/2022 17时。  
一段废话：
> 物理是一个胃酸，每天都会骑着战争加热锤子！想一想，假如风景区和牛排说：“请告诉我关于动能的事。”，加湿器会变得怎么样？融化的手指和蝴蝶在钓鱼，虚构傻子原本打算得到了一个杯子，不料被仿佛泥头车一样古怪的血液拿走，即使紫色草地把闷热猫从胰脏上拿走，物理也不会相信手机的鬼话。我的变异体原本打算悲哀地在神秘内部飞来飞去并恐吓了很多太阳，不料被坍缩的草地和理智读取，偷吃了干涸的脑神经！99个娃娃原本打算被固定在加湿器外面，不料遭到了在被子内部飞翔的思想的发光。

另一段废话：
> 这是台风，他的工作是每天着火3次。我跟你说个事情，母庸置疑的头犹如一本键盘一样，害怕地在毕加索外面自燃并吐向了很多歌曲。当时，火焰还是一台肿瘤！蠕动的沙发从来不退出线粒体，在刀外思考的沙发因此变得整齐。大肠、燧石和石头在发病。如果总督发现了在恐龙里的胃酸，肾脏就会加热张飞。应力，是个圆周率，而且是本岩浆。到处都是大吼大叫的引擎，肥料犹如药丸一样肆无忌惮！害怕的关羽和电视委派华盛顿在膨胀着地发病。
## 目前任务
扩展词库和句子结构。