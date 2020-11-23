package jp.techacademy.shirou.ogata.apiapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        var shop = intent.getSerializableExtra(KEY_SHOP) as Shop
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        web_toolbar.setTitle(R.string.app_name)
        setSupportActionBar(web_toolbar)

        val url = if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc
        webView.loadUrl(url)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        var shop = intent.getSerializableExtra(KEY_SHOP) as Shop
        val url = shop.couponUrls.toString()
        if(FavoriteShop.findBy(shop.id) != null){
            menu!!.findItem(R.id.favoritestar).setIcon(R.drawable.ic_star)
        }else{
            menu!!.findItem(R.id.favoritestar).setIcon(R.drawable.ic_star_border)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //☆が押された処理
        var shop = intent.getSerializableExtra(KEY_SHOP) as Shop
        if(item?.itemId == R.id.favoritestar){

            if(FavoriteShop.findBy(shop.id) != null){
                FavoriteShop.delete(shop.id)
                item.setIcon(R.drawable.ic_star_border)
            }else{
                FavoriteShop.insert(FavoriteShop().apply {
                    id = shop.id
                    name = shop.name
                    imageUrl = shop.logoImage
                    address = shop.address
                    url = if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc
                })
                item.setIcon(R.drawable.ic_star)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val KEY_SHOP = "shops"
        fun start(activity: Activity, shop: Shop) {
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra(KEY_SHOP, shop)
            activity.startActivity(intent)
        }
    }
}