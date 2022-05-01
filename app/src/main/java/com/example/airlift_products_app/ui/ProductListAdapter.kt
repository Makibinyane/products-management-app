package com.example.airlift_products_app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.airlift_products_app.R
import com.example.airlift_products_app.model.ProductResponse

class ProductListAdapter (private val onClick: (ProductResponse) -> Unit):
    ListAdapter<ProductResponse, ProductListAdapter.ProductViewHolder>(ProductDiffCallback()){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): ProductListAdapter.ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductListAdapter.ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtProductTitle: TextView = itemView.findViewById(R.id.txtDisplayProductTitle)
        var txtProductPrice: TextView = itemView.findViewById(R.id.txtDisplayProductPrice)
        var txtProductCategory: TextView = itemView.findViewById(R.id.txtDisplayProductCategory)

        fun bind(response: ProductResponse) {
            txtProductTitle.text = response.title
            txtProductCategory.text = response.category
            txtProductPrice.text = response.price.toString()

            itemView.setOnClickListener {
                onClick(response)
            }
        }
    }


    class ProductDiffCallback : DiffUtil.ItemCallback<ProductResponse>() {
        override fun areItemsTheSame(
            oldItem: ProductResponse,
            newItem: ProductResponse
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ProductResponse,
            newItem: ProductResponse
        ): Boolean {
            return oldItem.title == newItem.title
                    && oldItem.category == oldItem.category
        }
    }


}