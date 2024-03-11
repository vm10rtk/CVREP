//
//  NewReciepeView.swift
//  Recipe
//
//  Created by V M on 2.03.24.
//

import SwiftUI

struct NewReciepeView: View {
    @State private var showAddRecipe = false
    var body: some View {
        NavigationView {
            Button("Add recipe manually"){
                showAddRecipe = true
            }
                .navigationTitle("My Reciepes")
        }
        .navigationViewStyle(.stack)
        .sheet(isPresented: $showAddRecipe){
            AddRecipeView()
        }
    }
}

struct NewReciepeView_Previews: PreviewProvider {
    static var previews: some View {
        NewReciepeView()
    }
}
