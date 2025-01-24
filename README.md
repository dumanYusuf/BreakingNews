Bu uygulama, Clean Architecture prensiplerine uygun olarak tasarlanmış bir örnek API uygulamasıdır. Bu yapıda, Retrofit kütüphanesini kullanarak verileri ağ üzerinden alıp listeleme işlemleri gerçekleştirilmiştir. Ayrıca, Hilt kullanarak bağımlılıkları yönetim ve Use Case katmanı aracılığıyla Repository ve ViewModel arasındaki bağlantı sağlanmıştır.

Uygulamanın tasarımı şu şekilde yapılandırılmıştır:

Retrofit: Ağ veri alım işlemlerini yöneten bir API istemcisi olarak kullanılmıştır. Verileri çekmek ve listelemek için gerekli olan HTTP istekleri Retrofit aracılığıyla yapılmaktadır.

Hilt: Bağımlılıkları azaltmak ve yönetmek amacıyla kullanılmıştır. Hilt, DI (Dependency Injection) sağlayarak kodun test edilebilirliğini ve sürdürülebilirliğini artırmaktadır.

Clean Architecture: Uygulama, Clean Architecture prensiplerine uygun olarak katmanlara ayrılmıştır. Bu yaklaşım, uygulamanın her bir bileşeninin (veri, iş mantığı, ve kullanıcı arayüzü) birbirinden bağımsız olarak yönetilmesini sağlar.

Use Case: İş mantığının yönetildiği ve işlevlerin tek bir sorumluluk doğrultusunda düzenlendiği bir katmandır. Repository ve ViewModel arasındaki bağlantıyı sağlayarak kodun modülerliğini ve bakımını kolaylaştırır.

ViewModel: Kullanıcı arayüzü ile iş mantığı arasındaki köprüyü kurar ve kullanıcı etkileşimlerine yanıt verir.

Bu yapı, kodun okunabilirliğini, bakımını ve test edilebilirliğini artırır. Her bir fonksiyon ve sınıf, belirli bir işlevi yerine getirecek şekilde tasarlanmıştır, bu da uygulamanın daha düzenli ve yönetilebilir olmasını sağlar.

Ayrıca bana apidan dönen verilerin hepsnini alıp(dto) paketin içerisinde extensions fonksiyonu yardımıyla bana lazım olan verileri kullanarak kendi model sınıfıma fonksiyon yazdım ve bu sayede tüm verileri almama rağmen sadece bana lazım olan verileri kullanmış oldum.

Gelen verilerin isLoading,errormessage ve success olma durumlarına göre değerlendirdim. success olma durumunda listemi,isLoading durumunda CircularProgressIndicator ve olası bir hata durumunda errormessage olaylarınıda ele aldım.


<p align="center">
  <img src="https://github.com/dumanYusuf/BreakingNews/blob/master/news1.png?raw=true" width="400" />
  <img src="https://github.com/dumanYusuf/BreakingNews/blob/master/news2.png?raw=true" width="400" />
</p>

<p align="center">
  <img src="https://github.com/dumanYusuf/BreakingNews/blob/master/news3.png?raw=true" width="400" />
</p>




