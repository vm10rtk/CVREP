//
//  CategoryView.swift
//  Recipe
//
//  Created by V M on 4.03.24.
//

import SwiftUI

struct CategoryView: View {
    @EnvironmentObject var recipesVM:RecipesViewModel
    
    var category:Category
    
    //Computed property
    var recipes:[Recipe]{
        return recipesVM.recipes.filter{$0.category == category.rawValue}
    }
    var body: some View {
        ScrollView{
            RecipeList(recipes:recipes)
        }.navigationTitle(Text(category.rawValue + "s"))
    }
}
struct CategoryView_Previews: PreviewProvider {
    static var previews: some View {
        CategoryView(category: Category.dessert)
            .environmentObject(RecipesViewModel())
    }
}
