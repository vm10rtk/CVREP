//
//  RecipeApp.swift
//  Recipe
//
//  Created by V M on 2.03.24.
//

import SwiftUI

@main
struct RecipeApp: App {
    @StateObject var recipesViewModel = RecipesViewModel()
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(recipesViewModel)
        }
    }
}
