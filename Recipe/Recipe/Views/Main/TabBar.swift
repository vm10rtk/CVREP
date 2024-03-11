//
//  TabBar.swift
//  Recipe
//
//  Created by V M on 2.03.24.
//

import SwiftUI

struct TabBar: View {
    var body: some View {
        TabView{
            HomeView().tabItem {
                Label("Home", systemImage: "house")
            }
            
            CategoriesView().tabItem {
                Label("Categories", systemImage: "square.fill.text.grid.1x2")
            }
            
            NewReciepeView().tabItem {
                Label("New Reciepe", systemImage: "plus")
            }
            
            FavoritesView().tabItem {
                Label("Favorites", systemImage: "heart")
            }
            
            SettingsView().tabItem {
                Label("Settings", systemImage: "gear")
            }
        }
    }
}

struct TabBar_Previews: PreviewProvider {
    static var previews: some View {
        TabBar()
            .environmentObject(RecipesViewModel())
    }
}
