//
//  RecipiesViewModel.swift
//  Recipe
//
//  Created by V M on 4.03.24.
//

import Foundation

class RecipesViewModel: ObservableObject{
  @Published  private(set) var recipes:[Recipe]=[]
    
    init() {
        recipes = Recipe.all
    }
    
    func addRecipe(recipe:Recipe) {
        recipes.append(recipe)
    }
}
