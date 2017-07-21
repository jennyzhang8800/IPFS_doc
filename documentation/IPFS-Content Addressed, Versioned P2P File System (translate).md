对[Benet, Juan (2014) IPFS - Content Addressed, Versioned, P2P File System.](https://github.com/ipfs/papers/raw/master/ipfs-cap2pfs/ipfs-p2p-file-system.pdf)
的翻译整理。

链接：
+ [ipfs-github](https://github.com/ipfs)
+ [ipfs.io](https://ipfs.io/docs/getting-started/)

## IPFS - Content Addressed, Versioned, P2P File System
## IPFS-内容寻址，版本控制的P2P文件系统

### ABSTRACT
>The InterPlanetary File System (IPFS) is a peer-to-peer distributedle system that seeks to connect all computing devices with the same system of fles. In some ways, IPFS is similar to the Web, but IPFS could be seen as a single BitTorrent swarm, exchanging objects within one Git repository. In other words, IPFS provides a high throughput content-addressed block storage model, with content-addressed hyper links. This forms a generalized Merkle
DAG, a data structure upon which one can build versioned file systems, blockchains, and even a Permanent Web. IPFS
combines a distributed hashtable, an incentivized block exchange, and a self-certifying namespace. IPFS has no single
point of failure, and nodes do not need to trust each other.

摘要：星际文件系统（IPFS）是一个对等的分布式系统，旨在用相同的文件系统连接所有的计算机设备。在某些方面，IPFS和Web类似，但是IPFS能够被看做一个单一的BitTorrent群，在一个Git 仓库中交换对象。换句话说，IPFS用内容寻址超链接提供一个高吞吐量的，内容寻址的块存储模型。它形成一个整体的Merkle DAG，这是一个数据结构，据此可建立版本控制的文件系统，区块链，甚至是永久Web。IPFS结合分布式哈希表，激励块交换，和自我证明的命名空间。IPFS没有单点故障，节点不需要互相信任。

### 1. INTRODUCTION
>There have been many attempts at constructing a global distributed file system. Some systems have seen signifi
cant success, and others failed completely. Among the academic attempts, AFS [6] has succeeded widely and is still
in use today. Others [7, ?] have not attained the same success. Outside of academia, the most successful systems
have been peer-to-peer file-sharing applications primarily geared toward large media (audio and video). Most no-
tably, Napster, KaZaA, and BitTorrent [2] deployed large file distribution systems supporting over 100 million simul-
taneous users. Even today, BitTorrent maintains a massive deployment where tens of millions of nodes churn daily [16].These applications saw greater numbers of users and files distributed than their academic file system counterparts. However, the applications were not designed as infrastructure to be built upon. While there have been successful repurposings, no general file-system has emerged that others global,low-latency, and decentralized distribution.

在构建一个全球分布式文件系统方面，已经有许多尝试。有些系统已取得显著成功，其他系统完全失败。在学术界的尝试中，AFS [ 6 ]已经取得了广泛的成功，至今仍在继续使用。其的系统没有取得同样的成功。在学术界之外，最成功的系统主要是面向大型媒体（音频和视频）的对等文件共享应用程序。最值得注意的是，Napster，Kazaa和BitTorrent部署，[ 2 ]大文件分发系统支持超过1亿个并发用户。即使在今天，BitTorrent仍然保持着大规模的部署，每天有数以千万计的节点在流失。这些应用程序比他们的学术文件系统对应的用户和文件数量更多。然而，这些应用程序并没有被设计成基础设施。虽然有过成功的再利用，但没有出现能提供全球的，低延迟的，去中心化的通用的文件系统。

>Perhaps this is because a “good enough" system for most use cases already exists: HTTP. By far, HTTP is the most successful “distributed system of files" ever deployed. Coupled with the browser, HTTP has had enormous technical and social impact. It has become the de facto way to transmit files across the internet. Yet, it fails to take advantage of dozens of brilliant file distribution techniques invented in the last fifteen years. From one prespective, evolving Web infrastructure is near-impossible, given the number of backwards compatibility constraints and the number of strong parties invested in the current model. But from another perspective, new protocols have emerged and gained wide use since the emergence of HTTP. What is lacking is upgrading design: enhancing the current HTTP web, and introducing new functionality without degrading user experience.

这可能是因为对大多数用例来说，一个“足够好”系统已经存在：HTTP。到目前为止，HTTP是部署最成功的“分布式文件系统”。与浏览器结合，HTTP产生了巨大的技术和社会影响。它已经成为在因特网上传输文件的实际的方法。然而，它未能充分利用过去十五年发明的几十种出色的文件分发技术。从一个角度来看，考虑到向后兼容性约束的数量和在己经投入到当前模型中的政党数量, 不断发展的网络基础设施，几乎是不可能的。但是从另一个角度来看，自从HTTP出现以来，新的协议就应运而生并得到了广泛的应用。缺乏的是升级设计：增强当前的HTTP Web，以及引入新功能而不降低用户体验。

>Industry has gotten away with using HTTP this long because moving small files around is relatively cheap, even for
small organizations with lots of traffic. But we are entering a new era of data distribution with new challenges: (a)
hosting and distributing petabyte datasets, (b) computing on large data across organizations, (c) high-volume high-
denition on-demand or real-time media streams, (d) versioning and linking of massive datasets, (e) preventing ac-
cidental disappearance of important files, and more. Many of these can be boiled down to “lots of data, accessible ev-
erywhere." Pressed by critical features and bandwidth concerns, we have already given up HTTP for different data
distribution protocols. The next step is making them part of the Web itself.

长期以来，业界一直在使用HTTP，因为移动小文件是相对便宜的，即使对于拥有大量流量的小组织来说也是如此。但我们正在进入一个数据分发的新时代，面临的挑战有：(a)托管和分发PB级数据集,(b)跨组织的大数据计算,(c)大容量高清晰度点播或实时媒体流,(d)海量数据集的版本控制与链接,(e)防止重要文件的意外丢失，等等。这其中的许多问题可以归结为“大量数据，随处可访问”。由于关键特性和带宽问题的影响紧缺，对于不同的数据分发协议来说，我们已经放弃使用HTTP。下一步是使它们成为Web本身的一部分。

>Orthogonal to efficient data distribution, version control systems have managed to develop important data collaboration workflows. Git, the distributed source code version control system, developed many useful ways to model and
implement distributed data operations. The Git toolchain offers others versatile versioning functionality that large file distribution systems severely lack. New solutions inspired by Git are emerging, such as Camlistore [?], a personal file storage system, and Dat [?] a data collaboration toolchain and dataset package manager. Git has already influenced distributed file system design [9], as its content addressed Merkle DAG data model enables powerful file distribution
strategies. What remains to be explored is how this data structure can influence the design of high-throughput oriented file systems, and how it might upgrade the Web itself.

正交高效的数据分发，版本控制系统已经开发出了重要数据协作工作流。Git, 分布式源代码版本控制系统，开发了许多有用的建模和实现分布式数据操作的方法。Git工具提供了其他大型分布式文件系统严重缺乏的丰富的版本控制功能。以Git为灵感的新的解决方案层出不穷，如camlistore，是一个个人文件存储系统；Dat，是一个数据协作工具链和数据包管理工具。Git已经影响了分布式文件系统的设计，其内容寻址Merkle DAG数据模型驱动了强大的文件分配策略。有待探讨的是这种数据结构如何影响面向高吞吐量的文件系统的设计，以及它如何可能升级Web本身。

>This paper introduces IPFS, a novel peer-to-peer version controlled file system seeking to reconcile these issues. IPFS
synthesizes learnings from many past successful systems.Careful interface-focused integration yields a system greater
than the sum of its parts. The central IPFS principle is modeling all data as part of the same Merkle DAG

本文介始了IPFS，一个新的对等版本控制的文件系统，旨在调和这些问题。IPFS从过去的许多成功系统中综合学习。以接口为中心的精心集成产生一个大于其各部分之和的系统。IPFS中心的原则是把所有的数据作为相同的Merkle DAG的一部分建模。

### 2. BACKGROUND
> This section reviews important properties of successful peer-to-peer systems, which IPFS combines.

本节介绍IPFS所结合的成功的P2P系统的重要特性。

#### 2.1 Distributed Hash Tables
>Distributed Hash Tables (DHTs) are widely used to coordinate and maintain metadata about peer-to-peer systems.
For example, the BitTorrent Mainline DHT tracks sets of peers part of a torrent swarm.

分布式哈希表（DHT）广泛用于协调和保持P2P系统的元数据。例如，BitTorrent Mainline DHT跟踪一个torrent群的一部分peers集合。

#### 2.1.1 Kademlia DHT
>Kademlia [10] is a popular DHT that provides:
1. Efficient lookup through massive networks: queries on average contact dlog2(n)e nodes. (e.g. 20 hops for a
network of 10; 000; 000 nodes).
2. Low coordination overhead: it optimizes the number of control messages it sends to other nodes.
3. Resistance to various attacks by preferring long-lived nodes.
4. Wide usage in peer-to-peer applications, including Gnutella and BitTorrent, forming networks of over 20
million nodes [16].

Kademlia是一个受欢迎的分布式哈希表，它提供了：
1. 通过大规模网络的有效查找：查询平均连接log2N节点.(例如：一个10000000节点的网络有20跳)
2. 低协调开销：它优化发送到其他节点的控制消息的数量。
3. 通过偏爱长寿命节点抵抗各种攻击。
4. 在对等网络的广泛应用，包括Gnutella和BitTorrent，形成超过2000万节点的网络。

#### 2.1.2 Coral DSHT
>While some peer-to-peer file systems store data blocks directly in DHTs, this “wastes storage and bandwidth, as data
must be stored at nodes where it is not needed" [5]. The Coral DSHT extends Kademlia in three particularly important ways:
1. Kademlia stores values in nodes whose ids are“nearest"(using XOR-distance) to the key. This does not take
into account application data locality, ignores “far"nodes that may already have the data, and force“\nearest" nodes to store it, whether they need it or not.This wastes significant storage and bandwith. Instead,Coral stores addresses to peers who can provide the data blocks.
2. Coral relaxes the DHT API from get_value(key) to get_any_values(key) (the \sloppy" in DSHT). This
still works since Coral users only need a single (working) peer, not the complete list. In return, Coral can
distribute only subsets of the values to the \nearest"nodes, avoiding hot-spots (overloading all the nearest
nodes when a key becomes popular).
3. Additionally, Coral organizes a hierarchy of separate DSHTs called clusters depending on region and size.
This enables nodes to query peers in their region 

一些P2P文件系统直接把数据块存储在DHTs中，这种作法“浪费存储和带宽，因为数据
必须被存储在不需要的节点上”[5]。Kademlia在三个特别重要的方面扩展了DSHT：

1. Kademlia在id离key“最近”的节点上存储值(异或距离)。这并不考虑应用程序数据局部性，忽略己经有数据的“远”的节点，只关注最近的节点存储而不管节点是否需要。这浪费了有用的存储和带宽。相反，Coral存储地址到可以提供数据块的peers中。
2. Coral把DHT API 从 get_value(key) 放宽到 get_any_values(key)  （DSHT中的”sloppy”）.这同样是有用的，因为Coral用户只需要一个单一(工作中)的peer,而不需要整个列表。反过来，Coral只能将值的子集分配到“最近的”节点,避免热点(当一个密钥变得流行时，重载所有最近的节点)。
3. 此外，Coral组织一个独立的层次结构，DSHT称为基于区域和大小的集群，这使得节点能够查询其区域中的peers。

#### 2.1.3 S/Kademlia DHT
>S/Kademlia [1] extends Kademlia to protect against malicious attacks in two particularly important ways:
1. S/Kademlia provides schemes to secure NodeId generation, and prevent Sybill attacks. It requires nodes to
create a PKI key pair, derive their identity from it,and sign their messages to each other. One scheme
includes a proof-of-work crypto puzzle to make generating Sybills expensive.
2. S/Kademlia nodes lookup values over disjoint paths,in order to ensure honest nodes can connect to each
other in the presence of a large fraction of adversaries in the network. S/Kademlia achieves a success rate of
0.85 even with an adversarial fraction as large as half of the nodes.

S / Kademlia [ 1 ]用两个尤为重要手段对Kademlia进行扩展以对抗恶意攻击：

1. S/Kademlia提供一个方案来保护NodeId的生成，预防Sybill攻击。它要求节点创建一个PKI密钥对，从中提取它们的身份，并将它们的消息相互签名，一个方案包括工作证明密码拼图使生成sybills昂贵。
2. S/Kademlia节点通过不相交的路径查找值，以确保在网络中的存在很大一部分对手的时候，诚实的节点也可以互相连接。当对手占所有节点一半的时候，S/Kademlia甚至能实现0.85的成功率。

### 2.2 Block Exchanges - BitTorrent
>BitTorrent [3] is a widely successful peer-to-peer filesharing system, which succeeds in coordinating networks of untrusting peers (swarms) to cooperate in distributing pieces of files to each other. Key features from BitTorrent and its
ecosystem that inform IPFS design include:
1. BitTorrent's data exchange protocol uses a quasi tit-for-tat strategy that rewards nodes who contribute to
each other, and punishes nodes who only leech others' resources.
2. BitTorrent peers track the availability of file pieces,prioritizing sending rarest pieces first. This takes load
off seeds, making non-seed peers capable of trading with each other.
3. BitTorrent's standard tit-for-tat is vulnerable to some exploitative bandwidth sharing strategies. PropShare [8]
is a different peer bandwidth allocation strategy that better resists exploitative strategies, and improves the
performance of swarms.

2.2 块交换-BitTorrent
BitTorrent的[ 3 ]是一种被广泛利用的成功的P2P文件共享系统，它成功的在不信任节点网络中协调彼此的文件分发。被引入IPFS的BitTorrent及其生态系统的关键特征包括：

1. BitTorrent的数据交换协议使用了一种准针锋相对的策略，奖励互相贡献的节点，惩罚只挖掘他人资源的节点。
2. BitTorrent对等点跟踪文件片段的可用性，优先发送最稀有的片断。这就需要把种子卸下来，使没有种子的对等点能够相互交易。
3. BitTorrent的标准针锋相对是容易受到一些剥削性带宽共享策略的攻击。propshare [ 8 ]是一个不同的对等带宽分配策略，能更好地抵抗剥削性策略，提高群的性能。

### 2.3 Version Control Systems - Git
>Version Control Systems provide facilities to model files changing over time and distribute different versions efciently.
The popular version control system Git provides a powerful Merkle DAG  object model that captures changes to a file system tree in a distributed-friendly way.
1. Immutable objects represent Files (blob), Directories(tree), and Changes (commit).
2. Objects are content-addressed, by the cryptographic hash of their contents.
3. Links to other objects are embedded, forming a Merkle DAG. This provides many useful integrity and workflow properties.
4. Most versioning metadata (branches, tags, etc.) are simply pointer references, and thus inexpensive to cre-
ate and update.
5. Version changes only update references or add objects.
6. Distributing version changes to other users is simply transferring objects and updating remote references.

2.3 版本控制系统-Git
版本控制系统提供了模型文件随时间变化的工具，并有效地分发不同版本.流行的版本控制系统Git提供了一个强大的Merkle DAG对象模型，它以分布式友好方式捕获对文件系统树的更改。
1. 不可变对象代表文件（数据集）、目录（树）和更改（提交）
2. 对象通过内容的加密散列进行内容寻址
3. 其他对象嵌入链接，形成一个Merkle DAG。这提供了许多有用的完整性和工作流属性。
4. 新版本的元数据（标签，分支，等）是用指针引用到，因此可以廉价的创建和更新。
5. 版本更改只更新引用或添加对象
6. 将版本更改分发给其他用户仅仅是传递对象和更新远程引用。

### 2.4 Self-Certified Filesystems - SFS
SFS [12, 11] proposed compelling implementations of both(a) distributed trust chains, and (b) egalitarian shared global
namespaces. SFS introduced a technique for building Self Certified Filesystems: addressing remote file systems using
the following scheme
/sfs/<Location>:<HostID>
where Location is the server network address, and:
HostID = hash(public_key || Location)
Thus the name of an SFS file system certifies its server.The user can verify the public key offered by the server,
negotiate a shared secret, and secure all traffic. All SFS instances share a global namespace where name allocation
is cryptographic, not gated by any centralized body.

2.4 自认证的文件系统-SFS
SFS提供了（a）发布式信任链和（b）平等共享全局命名空间 两者的强制实现。SFS介绍了构建自证明的文件系统的技术。使用以下方案寻址远程文件系统：
/sfs/<Location>:<HostID>
Location是服务器网络地址，而且：
HostID = hash(public_key || Location)
因此一个SFS文件系统名称认证了它自己的服务器，用户可以验证服务器提供的公钥，协商共享机密，并确保所有的通信。所有SFS实例共享一个全局名称空间，其中名称分配是加密的，而不是由任何集中式机构控制的

### 3.IPFS DESIGN
IPFS 设计
>IPFS is a distributed file system which synthesizes successful ideas from previous peer-to-peer sytems, including
DHTs, BitTorrent, Git, and SFS. The contribution of IPFS is simplifying, evolving, and connecting proven techniques
into a single cohesive system, greater than the sum of its parts. IPFS presents a new platform for writing and de-
ploying applications, and a new system for distributing and versioning large data. IPFS could even evolve the web itself.

IPS是一个分布式文件系统，它成功的综合了以前的p2p系统的思想，包括DHTs,BitTorrent,Git 和SFS. 
IPFS的贡献是简化,进化并且连接己经经过验证的技术到一个单一的紧密结合的系统中，达到整体大于各部分之和的效果。它提供了一个新的平台来编写和部署应用程序，以及一个分配和版本化大数据的新系统。它甚至可以进化Web本身。

>IPFS is peer-to-peer; no nodes are privileged. IPFS nodes store IPFS objects in local storage. Nodes connect to each
other and transfer objects. These objects represent files and other data structures. The IPFS Protocol is divided into a
stack of sub-protocols responsible for dierent functionality:


1. Identities - manage node identity generation and verification. Described in Section 3.1.
2. Network - manages connections to other peers, uses various underlying network protocols. Configurable.
Described in Section 3.2.
3. Routing - maintains information to locate specific peers and objects. Responds to both local and re-
mote queries. Defaults to a DHT, but is swappable.Described in Section 3.3.
4. Exchange - a novel block exchange protocol (BitSwap)that governs efficient block distribution. Modelled as
a market, weakly incentivizes data replication. Trade Strategies swappable. Described in Section 3.4.
5. Objects - a Merkle DAG of content-addressed immutable objects with links. Used to represent arbitrary datastructures, e.g. file hierarchies and communication systems. Described in Section 3.5.
6. Files - versioned file system hierarchy inspired by Git.
Described in Section 3.6.
7.Naming - A self-certifying mutable name system. Described in Section 3.7.

IPFS是对等的；没有节点享有特权。IPFS节点在本地存储IPFS对象。节点相互连接并传输对象。这些对象表示文件和其他数据结构。IPFS协议分为负责不同功能的一堆子协议。

1. 身份-管理节点身份生成和验证，在3.1节描述。
2. 网络-管理与其他节点的连接，使用各种底层网络协议。可配置。在3.2节描述。
3. 路由-维护信息以定位特定的对等点和对象.响应本地和远程查询。默认为DHT，但是可替换。在3.3节中描述。
4. 交换-一个新的块交换协议（BitSwap）,管理有效的块分配。在3.4节中描述。
5. 对象-一个有链接的内容寻址的不可改变的对象的Merkle DAG。用于表示任意数据结构。例如：文件层次结构和通信系统。在3.5节描述。
6. 文件-版本控制的文件系统，由Git启发分层。在3.6节中描述。
7. 命名-一个自认证的可变名称系统。第3.7节中描述。

>These subsystems are not independent; they are integrated and leverage blended properties. However, it is useful to describe them separately, building the protocol stack from the bottom up.
Notation: data structures and functions below are specified in Go syntax.

这些子系统不是独立的,它们是综合的，并利用混合属性。然而，分开描述是有用的，从底层构建协议栈。

标记：下面的数据结构和函数在GO语法中指定。

#### 3.1 Identities
>Nodes are identified by a NodeId, the cryptographic hash3 of a public-key, created with S/Kademlia's static crypto puz-
zle [1]. Nodes store their public and private keys (encrypted with a passphrase). Users are free to instatiate a “new" node identity on every launch, though that loses accrued network benefits. Nodes are incentivized to remain the same.

节点由NodeId鉴定，NodeId是公钥的加密散列，由S/Kademia的静态密码之迷创建。节点存储它们的公钥和私钥（用密码加密）. 虽然失去了应有的网络效益，但每一次发布时用户可以自由地实例化一个“新的”节点身份。节点激励保持不变。
```
type NodeId Multihash
type Multihash []byte
// self-describing cryptographic hash digest
type PublicKey []byte
type PrivateKey []byte
// self-describing keys
type Node struct {
NodeId NodeID
PubKey PublicKey
PriKey PrivateKey
}
```
>S/Kademlia based IPFS identity generation: 

基于S/Kademlia的IPFS身份生成:

```
difficulty = <integer parameter>
n = Node{}
do {
n.PubKey, n.PrivKey = PKI.genKeyPair()
n.NodeId = hash(n.PubKey)
p = count_preceding_zero_bits(hash(n.NodeId))
} while (p < difficulty)
Upon first connecting, peers exchange public keys, and check: hash(other.PublicKey) equals other.NodeId. If
not, the connection is terminated.
```
在第一次连接时，节点交换公钥，并检查：hash（other.PublicKey）=other.nodeid。如果不相等，则连接终止。

>Note on Cryptographic Functions.

加密函数的注意事项：
>Rather than locking the system to a particular set of function choices, IPFS favors self-describing values. Hash digest values are stored in multihash format, which includes a short header specifying the hash function used, and the
digest length in bytes. Example:
<function code><digest length><digest bytes>
This allows the system to (a) choose the best function for the use case (e.g. stronger security vs faster performance),
and (b) evolve as function choices change. Self-describing values allow using different parameter choices compatibly.

IPFS更喜欢自描述的值，而不是锁定系统到一组特定的函数选择中。Hash摘要的值被存储在一个multihash格式中，它包括一个指明所使用的hash函数的短头部，以及摘要长度。例如：
           ```<function code><digest length><digest bytes>```
它允许系统(a)为用例选择最佳函数（例如：更强的安全性VS更快的性能），（b）随着功能选择的变化而演化.
自描述的值允许使用合适的不同的参数选择

### 3.2 Network
>IPFS nodes communicate regualarly with hundreds of other nodes in the network, potentially across the wide internet.
The IPFS network stack features:
* Transport: IPFS can use any transport protocol,and is best suited for WebRTC DataChannels [?] (for browser connectivity) or uTP(LEDBAT [14]).
* Reliability: IPFS can provide reliability if underlying networks do not provide it, using UTP (LEDBAT [14])
or SCTP [15].
* Connectivity: IPFS also uses the ICE NAT traversal techniques [13].
* Integrity: optionally checks integrity of messages using a hash checksum.
* Authenticity: optionally checks authenticity of messages using HMAC with sender's public key.

IPFS 节点节点定期在网络上的其他节点通信，跨越广域网。

IPFS网络的特点：
* 传输：IPFS可以使用任何传输协议，它最适合WebRTC DataChannels
* 可靠性：如果底层网络不提供可靠性，IPFS能提供。如当使用UTP或SCTP时。
* 连通性：IPFS也使用ICE NAT 穿越技术
* 完整性：可选择使用hash校验和检查消息的完整性。
* 真实性：可以选择使用HMAC与发送方的公钥检查消息的真实性。

#### 3.2.1 Note on Peer Addressing
3.2.1关于对等地址的注释
>IPFS can use any network; it does not rely on or assume access to IP. This allows IPFS to be used in overlay networks.
IPFS stores addresses as multiaddr formatted byte strings for the underlying network to use. multiaddr provides a way
to express addresses and their protocols, including support for encapsulation. For example:
``` #an SCTP/IPv4 connection
 /ip4/10.20.30.40/sctp/1234/
   #an SCTP/IPv4 connection proxied over TCP/IPv4
/ip4/5.6.7.8/tcp/5678/ip4/1.2.3.4/sctp/1234/
```
IPFS可以使用任何网络；它不依赖或假定访问IP. 这允许IPFS用于覆盖网络。对于使用底层网络，IPFS存储地址作为格式化的多地址字符串。多地址提供了一种方法来表示地址和协议。包括对封装的支持。例如：
```
#一个SCTP/IPv4连接
 /ip4/10.20.30.40/sctp/1234/
#一个基于TCP/IP代理的SCTP/IPv4连接
/ip4/5.6.7.8/tcp/5678/ip4/1.2.3.4/sctp/1234/
```

### 3.3 Routing
>IPFS nodes require a routing system that can find (a)other peers' network addresses and (b) peers who can serve
particular objects. IPFS achieves this using a DSHT based on S/Kademlia and Coral, using the properties discussed in
2.1. The size of objects and use patterns of IPFS are similar to Coral [5] and Mainline [16], so the IPFS DHT makes a
distinction for values stored based on their size. Small values (equal to or less than 1KB) are stored directly on the DHT. For values larger, the DHT stores references, which are the NodeIds of peers who can serve the block.
The interface of this DSHT is the following:

IPFS节点需要一个路由系统，该路由系统可以找到(a)其他对等点的网络地址 (b)能为特定对象服务的对等点。

IPFS使用基于S/Kademlia 和Coral的DSHT实现上述功能，使用的是2.1节所讨论的属性。对象的大小和IPFS的用模式与Coral和Mainline类似,因此IPFS DHT根据存储的大小对存储的值进行区分。小值（等于或小于1KB）直接存储在DHT。对于较大的值，DHT存储引用，引用是能服务块的对等点的NodeId.
DSHT的接口如下：
```
type IPFSRouting interface {
FindPeer(node NodeId)
/ / gets a particular peer's network address
SetValue(key []bytes, value []bytes)
// stores a small metadata value in DHT
GetValue(key []bytes)
// retrieves small metadata value from DHT
ProvideValue(key Multihash)
// announces this node can serve a large value
FindValuePeers(key Multihash, min int)
// gets a number of peers serving a large value
}
Note: different use cases will call for substantially different routing systems (e.g. DHT in wide network, static HT
in local network). Thus the IPFS routing system can be swapped for one that fits users' needs. As long as the interface above is met, the rest of the system will continue to function.

```
注释：不同的用例将调用实质上不同的路由系统。(例如：广域网中的DHT，局域网中的静态HT).因此，IPFS路由系统可以换成符合用户需求的路由系统。只要满足上面的接口，系统的其余部分将继续运行。

### 3.4 Block Exchange - BitSwap Protocol
>In IPFS, data distribution happens by exchanging blocks with peers using a BitTorrent inspired protocol: BitSwap.
Like BitTorrent, BitSwap peers are looking to acquire a set of blocks (want_list), and have another set of blocks to of-
fer in exchange (have_list). Unlike BitTorrent, BitSwap is not limited to the blocks in one torrent. BitSwap operates as a persistent marketplace where node can acquire the blocks they need, regardless of what files those blocks are
part of. The blocks could come from completely unrelated files in the filesystem. Nodes come together to barter in the
marketplace.

3.4 块交换-BitSwap 协议

在IPFS中,对等点交换块通过使用由BitTorrent启发的协议:BitSwap来实现数据分配。和BitToreent类似的是，BitSwap对等点希望获得一组块（want_list）,并且拥有另外一组块提供交换(have_list)。与BitTorrent不同的是：BitSwap不把块限定在一个torrent. BitSwap作为一个持续的市场运作，无论这些块属于哪个文件，节点都可以获得他们所需要的块。这些块可能来自文件系统中完全无关的文件。节点聚集在一起进行市场交易。

>While the notion of a barter system implies a virtual currency could be created, this would require a global ledger to
track ownership and transfer of the currency. This can be implemented as a BitSwap Strategy, and will be explored in
a future paper.

虽然易货制度的概念意味着可以创建虚拟货币,但这就需要一个全球分类账来追踪货币的所有权和转移。这可以作为一个bitswap战略来实现，并将在未来的论文中探索

>In the base case, BitSwap nodes have to provide direct value to each other in the form of blocks. This works fine when the distribution of blocks across nodes is complementary, meaning they have what the other wants. Often, this
will not be the case. In some cases, nodes must work for their blocks. In the case that a node has nothing that its
peers want (or nothing at all), it seeks the pieces its peers want, with lower priority than what the node wants itself.
This incentivizes nodes to cache and disseminate rare pieces,even if they are not interested in them directly.

在基本案例中，BitSwap节点必须以块的形式向对方提供直接的值。当跨节点的块分配是互补的时，这种方法很好，意思是节点正好有其他想要的。通常情况下，并非如此。在某些情况下，节点必须为它们的块工作。在一个节点没有它的对等节点想要的块的情况下（或该节点根本没有块），它将以比本身节点需求更低的优先级去寻找它的对等节点想要的部分。这鼓励节点缓存和传播稀有部分，即使节点本身对这些稀有部分不感兴趣。

#### 3.4.1 BitSwap Credit
>The protocol must also incentivize nodes to seed when they do not need anything in particular, as they might have
the blocks others want. Thus, BitSwap nodes send blocks to their peers optimistically, expecting the debt to be repaid.
But leeches (free-loading nodes that never share) must be protected against. A simple credit-like system solves the
problem:

当节点不需要块的时候，该协议还必须激励节点做种子，因为他们可能拥有其他节点想要的块。
因此，BitSwap节点能乐观地发送块给对等点，并预计需要偿还的债务。但水蛭（免费加载节点不共享）必须防止。一个简单的信用状系统解决了这个问题：
1. Peers track their balance (in bytes verified) with other nodes.
2. Peers send blocks to debtor peers probabilistically, according to a function that falls as debt increases.
Note that if a node decides not to send to a peer, the node subsequently ignores the peer for an ignore_cooldown timeout. This prevents senders from trying to game the probability by just causing more dice-rolls. (Default BitSwap is
10 seconds).

1. 对等节点跟踪与其他节点的平衡（以字节验证）。
2. 对等节点概率性地发送块给债务人节点，根据随债务增加而递减的函数。
注意，如果一个节点决定不发送给对等节点，这个节点在一个ignore_cooldown 超时随后忽略了对等节点.这可以防止发送者试图通过掷更多的骰子来进行游戏。（默认bitswap 是10秒）
