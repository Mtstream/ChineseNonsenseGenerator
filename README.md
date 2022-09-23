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
* `"name"`：名字（例：鲁迅）
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
`"/name/said：“~tsta”"` -> 鲁迅说过：“圆周率是难以忘怀的”
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
## 示例
时不时更新，最后更新：16/9/2022 17时。  
一段废话：
> 紫色的胰脏在崩溃，我跟你说，事实上难以理解的地球在咆哮，不要相信空间的鬼话。纸巾在结合。犹如鞋子一样安静的恐龙在痉挛！宛如牛排一样冰冻的动能在跳舞！皮肤安装了混沌！椅子龟速地把月饼摊开成歌曲之后，预备课程可怜得自裁，时间难以置信地害怕，如果高级的马桶很百年，头像就会搁浅，我跟你说个故事，垃圾桶阴阳怪气地把壁纸拉成梦，然后仿佛爆米花一样永恒的垃圾桶难以置信地强大，最后真是愚蠢，硬盘宛如太阳一样暗淡，办公室在废物里生存。

另一段废话：
> 我跟你说，这是悲伤的，而且没有一点燃烧！米桶在喷气。腹鳍的图像在潜水，如果巧克力肆无忌惮地杀菜刀，杯子就会自爆。宇宙像观点一样地轰炸牛排，正是因为如此，语言想：“沙发仿佛一个火山”！像小行星的行星在反弹！联合国说：“肿瘤犹如一个变压器”，但这明显错得像发癫的可乐。像陨石的鲁迅炸了电脑的同时，鞋子仿佛物流一样十分愤怒，我觉得你说的不对窗户原地地把可乐炸成电平，宛如面粉一样微小的成绩十分害怕。火山在爬行。
## 目前任务
扩展词库和句子结构。