//
//  SettingsView.swift
//  Recipe
//
//  Created by V M on 2.03.24.
//

import SwiftUI

struct SettingsView: View {
    var body: some View {
        NavigationView {
            Text("v 1.0.0")
                .navigationTitle("Settings")
        }
        .navigationViewStyle(.stack)
    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView()
    }
}
